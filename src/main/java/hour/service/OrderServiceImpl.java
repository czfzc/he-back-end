package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.util.NetUtil;
import hour.model.Order;
import hour.model.User;
import hour.repository.OrderRepository;
import hour.repository.UserRepository;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

import static hour.util.CodeUtil.md5;
import static hour.util.NetUtil.postData;
import static hour.util.StringUtil.*;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PreorderService preorderService;

    @Autowired
    UserService userService;

    @Autowired
    EntityManager entityManager;

    @Override
    public String payOrder(String ip, String mysession,JSONArray preorders){
        User user=userRepository.findByMysession(mysession);
        if(user==null) return createStatus(false);
        String open_id=user.getOpenId();
        String user_id=user.getUserId();
        String order_id=UUID.randomUUID().toString().replace("-","");

        Order order=new Order();

        order.setAbled(true);
        order.setIp(ip);
        order.setOrderId(order_id);
        order.setPayed(0);
        order.setTime(new Date());
        order.setTotalFee(0D);
        order.setUserId(user_id);
        orderRepository.save(order);

        if(preorderService.preorderIt(preorders,order_id,user_id)){
            double total=preorderService.calculateTotal(order_id);
            order.setTotalFee(total);
            orderRepository.save(order);
            if(total==0){
                order.setPayed(1);
                orderRepository.save(order);
                return createStatus(true);
            }else return this.unifiedorder(order_id,ip,open_id,(int)total*100).toJSONString();
        }else return createStatus(false);

    }



    /**
     * 用户微信支付完成时回调接口调用此函数
     * @param xml
     * @return
     * @throws Exception
     */

    @Override
    public boolean onFinishPayed(String xml){
        System.out.println(xml);
        try{
            Document doc=DocumentHelper.parseText(xml);
            Element ele=doc.getRootElement();

            if("SUCCESS".equals(ele.elementText("return_code"))&&"SUCCESS".equals(ele.elementText("result_code"))){
                String open_id=ele.elementText("openid");
                String order_id=ele.elementText("out_trade_no");
                float total=Float.valueOf(ele.elementText("total_fee"))/100;
                String weixin_orderid= ele.elementText("transaction_id");
                String finish_time= ele.elementText("time_end");

                String user_id=userRepository.findByOpenId(open_id).getUserId();

                Order order=orderRepository.findByOrderIdAndUserId(order_id,user_id);

                order.setPayed(1);

                orderRepository.save(order);

                //此处如果失败应当报警

                return true;
            }else return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 调用统一下单接口
     */

    @Value("${wexin.mchid}")
    private String mch_id;
    @Value("${wexin.appid}")
    private String appid;
    @Value("${wexin.mykey}")
    private String mykey;
    @Value("${wexin.onfinish}")
    private String onFinish;

    private JSONObject unifiedorder(String orderid, String ip, String openid, Integer total){
        String nonce_str= UUID.randomUUID().toString().replace("-", "");
        Map<String,String> content=new LinkedHashMap<String,String>();
        content.put("!appid", appid);																						//小程序appid
        content.put("!body", "express");																					//商品描述
        content.put("!mch_id", mch_id);																						//商户id
        content.put("!nonce_str", nonce_str);																				//随机数 不超过32位
        content.put("!notify_url", onFinish);													//订单完成的会调接口
        content.put("!openid", openid);																						//用户身份标识
        content.put("!out_trade_no", orderid);																				//订单id
        content.put("!spbill_create_ip", ip);																				//用户ip地址
        content.put("!total_fee", String.valueOf((int)(total*100)));															//总价
        content.put("!trade_type", "JSAPI");																				//接口种类 此为小程序

        content.put("!sign", this.calculateSign(content,mykey));
        String xml=xmlCreater(content);
        System.out.println(xml);
        String prepay= NetUtil.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder",xml);
        try {
            System.out.println(new String(prepay.getBytes(),"UTF-8"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this.orderXmlToJson(prepay, appid, mykey);
    }

    /**
     * 将微信统一下单的xml转化成json 通知微信小程序
     * @param xml
     * @param appid
     * @param mykey
     * @return
     */

    private JSONObject orderXmlToJson(String xml,String appid,String mykey){
        JSONObject toret=new JSONObject();
        try {
            Document doc= DocumentHelper.parseText(xml);
            Element ele=doc.getRootElement();
            if("FAIL".equals(ele.element("return_code").getText()))
                return new JSONObject(){
                    {
                        this.put("status", false);
                    }
                };
            toret.put("timeStamp", String.valueOf(System.currentTimeMillis()));
            toret.put("appId", appid);
            toret.put("nonceStr", UUID.randomUUID().toString().replace("-", ""));
            toret.put("signType", "MD5");
            toret.put("package", "prepay_id="+ele.element("prepay_id").getText());
            toret.put("paySign", this.calculateSign(toret, mykey));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new JSONObject(){
                {
                    this.put("status", false);
                }
            };
        }
        return toret;

    }

    /**
     * 计算微信接口的sign
     * @param map
     * @param mykey
     * @return
     */

    private String calculateSign(Map map,String mykey){
        String toret="";
        Collection<String> keyset=map.keySet();
        List<String> list=new ArrayList<String>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            toret+=(list.get(i).replaceFirst("!", "")+"="+map.get(list.get(i))+"&");
        }

        toret+="key="+mykey;
        System.out.println(toret);
        return md5(toret).toUpperCase();
    }

    /**
     * 退款
     * @return
     */
    @Override
    public boolean refundOrder(String orderid){

        Order order=orderRepository.findByOrderId(orderid);

        if(order==null)
            return false;

        if(order.getPayed()!=1)
            return false;

        Double total=order.getTotalFee();

        Map<String,String> content=new LinkedHashMap<String,String>();
        content.put("!appid", appid);																						//小程序appid
        content.put("!mch_id", mch_id);																						//商户id
        content.put("!nonce_str", getRandom(10));																				//随机数 不超过32位
        //	content.put("!notify_url", "https://shop.wly01.cn/myshop/onFinishedPayed");											//退款完成的会调接口
        content.put("!out_trade_no", orderid);																				//订单id
        content.put("!out_refund_no", orderid);																				//退款id
        content.put("!total_fee", String.valueOf(total));														//订单总价
        content.put("!refund_fee", String.valueOf(total));														//退款金额
        //	content.put("refund_desc", "sold_out");																				//退款原因
        content.put("!sign", this.calculateSign(content,mykey));
        String xml=xmlCreater(content);
        System.out.println(xml);
        try {
            Resource res = new ClassPathResource("cer.p12");
            String cerPath=res.getFile().getPath();
            String refund=postData("https://api.mch.weixin.qq.com/secapi/pay/refund", xml, mch_id, cerPath);
            System.out.println(new String(refund.getBytes(),"UTF-8"));
            if(refund.contains("FAIL"))
                return false;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 获取订单
     */
    @Override
    public Page<Order> getOrder(Integer page, Integer size){
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Order> orders=orderRepository.findAll(pageable);
        for(Iterator<Order> i=orders.iterator();i.hasNext();){
            Order order=i.next();
            order.setPreorder(preorderService.getAllPreorderByOrderId(order.getOrderId()));
        }
        return orders;
    }

    /**
     * 获取总订单数
     * @return
     */

    @Override
    public Long getCount(){
        String sql="select count(a.orderId) from Order a";
        Query query = entityManager.createQuery(sql);
        return (Long)query.getSingleResult();
    }

    /**
     * 获取搜索的总订单数
     * @return
     */

    /**
     * 根据订单号搜索订单
     */
    @Override
    public Page<Order> searchOrderById(String value, Integer page, Integer size){
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Order> orders=orderRepository.findAllByOrderIdContaining(value,pageable);
        for(Iterator<Order> i=orders.iterator();i.hasNext();){
            Order order=i.next();
            order.setPreorder(preorderService.getAllPreorderByOrderId(order.getOrderId()));
        }
        return orders;
    }

    /**
     * 根据用户账号搜索订单
     */
    @Override
    public Page<Order> searchOrderByUserId(String value, Integer page, Integer size){
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Order> orders=orderRepository.findAllByUserIdContaining(value,pageable);
        for(Iterator<Order> i=orders.iterator();i.hasNext();){
            Order order=i.next();
            order.setPreorder(preorderService.getAllPreorderByOrderId(order.getOrderId()));
        }
        return orders;
    }

    @Override
    public double calcuTotal(JSONArray preorders){

        double sum=0;

        for(int i=0;i<preorders.size();i++){
            JSONObject preorder=preorders.getJSONObject(i);
            if(preorder.getInteger("service_id")==1){  //快递代取服务的id为1
                sum+=preorderService.cacuTotalByObject(preorder);
            }else if(preorder.getInteger("service_id")==9){
                sum+=10;
            }
        }

        return sum;

    }

}
