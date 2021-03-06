package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import hour.model.*;
import hour.repository.*;
import hour.util.NetUtil;
import hour.util.StringUtil;
import hour.util.TimeUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

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

    @Autowired
    ExpressMonthCardService expressMonthCardService;

    @Autowired
    PreorderRepository preorderRepository;

    @Autowired
    MoreProductRepository moreProductRepository;

    @Autowired
    UserProductService userProductService;

    @Autowired
    ShopProductService shopProductService;

    @Autowired
    CardService cardService;

    @Autowired
    CardTypeRepository cardTypeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String payOrder(String ip, String mysession,JSONArray preorders){
        User user=userService.getUser(mysession);
        if(user==null) return createStatus(false);
        String open_id=user.getOpenId();
        String user_id=user.getUserId();

        ip=ip.split(":")[0];

        Order order=new Order();

        order.setAbled(true);
        order.setIp(ip);
        order.setPayed(0);
        order.setTime(new Date());
        order.setTotalFee(0D);
        order.setUserId(user_id);
        Order order1=orderRepository.save(order);
        String order_id=order1.getOrderId();

        if(preorderService.preorderIt(preorders,order_id,user_id)){
            double total=preorderService.calculateTotal(order_id);
            order.setTotalFee(total);
            orderRepository.save(order);
            if(total==0){
                order.setPayed(1);
                this.finishIt(order_id);
                Order order2=orderRepository.save(order);
                return createStatus(order2.getPayed()==1);
            }else{
                JSONObject result=this.unifiedorder(order_id,ip,open_id,(int)(total*100));
                String prepay_id=result.getString("package").replaceFirst("prepay_id=","");
                order1.setPrepayId(prepay_id);
                orderRepository.save(order1);
                return result.toJSONString();
            }
        }else throw new RuntimeException("unify order fail");

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String cardPayOrder(String user_id, String card_type_id){
        String ip="127.0.0.1";

        Order order=new Order();
        User user = userRepository.findByUserId(user_id);

        order.setAbled(true);
        order.setIp(ip);
        order.setPayed(0);
        order.setTime(new Date());
        order.setTotalFee(0D);
        order.setUserId(user_id);
        Order order1=orderRepository.save(order);
        String order_id=order1.getOrderId();

        JSONArray preorders = new JSONArray(){
            {
                this.add(new JSONObject(){
                    {
                        this.put("service_id",9);
                        this.put("card_type_id",card_type_id);
                    }
                });
            }
        };

        if(preorderService.preorderIt(preorders,order_id,user_id)){
            double total=preorderService.calculateTotal(order_id);
            order.setTotalFee(total);
            orderRepository.save(order);
            if(total==0){
                order.setPayed(1);
                this.finishIt(order_id);
                Order order2=orderRepository.save(order);
                return createStatus(order2.getPayed()==1);
            }else{
                JSONObject result=this.unifiedorder(order_id,ip,user.getOpenId(),(int)(total*100));
                String prepay_id=result.getString("package").replaceFirst("prepay_id=","");
                order1.setPrepayId(prepay_id);
                orderRepository.save(order1);
                return result.toJSONString();
            }
        }else throw new RuntimeException("unify order fail");
    }

    @Value("${order.max-time-min}")
    int max_time;
    @Override
    public String repayOrder(String user_id, String order_id){
        Order order=orderRepository.findByOrderIdAndUserId(order_id,user_id);
        if(order==null) return createStatus(false);
        if(!order.isAbled()) return createStatus(false);
        if(order.getPayed()!=0) return createStatus(false);
        if(order.getTotalFee()==0) return createStatus(false);
        if(TimeUtil.getTimeDiffMin(new Date(),order.getTime())>max_time) return createStatus(false);

        return this.unifiedorder(order.getOrderId(),order.getIp(),
                userRepository.findByUserId(user_id).getOpenId(),(int)(order.getTotalFee()*100)).toJSONString();

    }




    /**
     * 用户微信支付完成时回调接口调用此函数
     * @param xml
     * @return
     * @throws Exception
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean onFinishPayed(String xml){
        System.out.println(xml);
        try{
            Document doc=DocumentHelper.parseText(xml);
            Element ele=doc.getRootElement();

            if("SUCCESS".equals(ele.elementText("return_code"))&&"SUCCESS".equals(ele.elementText("result_code"))){
                String open_id=ele.elementText("openid");
                String order_id=ele.elementText("out_trade_no");

                String user_id=userRepository.findByOpenId(open_id).getUserId();

                Order order=orderRepository.findByOrderIdAndUserId(order_id,user_id);

                order.setPayed(1);

                orderRepository.save(order);
                //此处如果失败应当报警

                //对预付单做特异性操作
                return this.finishIt(order_id);

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
    @Value("${wexin.key}")
    private String appkey;
    @Value("${wexin.mykey}")
    private String mykey;
    @Value("${wexin.onfinish}")
    private String onFinish;

    private JSONObject unifiedorder(String orderid, String ip, String openid, Integer total){
        String nonce_str= UUID.randomUUID().toString().replace("-", "");
        Map<String,String> content=new LinkedHashMap<String,String>();
        content.put("!appid", appid);																						//小程序appid
        content.put("!body", "快递代取");																					//商品描述
        content.put("!mch_id", mch_id);																						//商户id
        content.put("!nonce_str", nonce_str);																				//随机数 不超过32位
        content.put("!notify_url", onFinish);													//订单完成的会调接口
        content.put("!openid", openid);																						//用户身份标识
        content.put("!out_trade_no", orderid);																				//订单id
        content.put("!spbill_create_ip", ip);																				//用户ip地址
        content.put("!total_fee", String.valueOf(total));															//总价
        content.put("!trade_type", "JSAPI");																				//接口种类 此为小程序

        content.put("!sign", StringUtil.calculateSign(content,mykey));
        String xml=xmlCreater(content);
        System.out.println(xml);
        String prepay= NetUtil.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder",xml);

        try {

            System.out.println(new String(prepay.getBytes(),"UTF-8"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject toret = this.orderXmlToJson(prepay, appid, mykey);
        toret.put("orderId",orderid);
        return toret;
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
            toret.put("paySign", StringUtil.calculateSign(toret, mykey));
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
     * 获取订单
     */
    @Override
    public Page<Order> getOrder(Integer page, Integer size){
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Order> orders=orderRepository.findAllByAbledTrue(pageable);
        for(Iterator<Order> i=orders.iterator();i.hasNext();){
            Order order=i.next();
            List<Preorder> preorders=preorderService.getAllPreorderByOrderId(order.getOrderId());
            if(preorders.size()==0) {
                i.remove();
                continue;
            }
            order.setPreorder(preorders);
        }
        return orders;
    }


    /**
     * 获取单个订单
     */
    @Override
    public Order getOrderById(String orderId){
        System.out.println("orderid = "+orderId);
        Order order=orderRepository.findByOrderId(orderId);
        if(order == null)
            throw new RuntimeException("invalid order_id");
        List<Preorder> preorders=preorderService.getAllPreorderByOrderId(orderId);
        order.setPreorder(preorders);
        return order;
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

    /**
     * 计算订单总价
     * @param preorders
     * @return
     */
    @Value("${express.card.product-id}")
    String expressCardProductId;

    @Override
    public double calcuTotal(JSONArray preorders){

        double sum=0;

        for(int i=0;i<preorders.size();i++){
            JSONObject preorder=preorders.getJSONObject(i);
            if(preorder.getInteger("service_id")==1){  //快递代取服务的id为1
                sum+=preorderService.cacuExpressTotalByObject(preorder);
            }else if(preorder.getInteger("service_id")==2){
                sum+=shopProductService.calcuTotal(preorder.getJSONArray("products"));
            }else if(preorder.getInteger("service_id")==9){
                sum+=moreProductRepository.findFirstByProductId(expressCardProductId).getSum();
            }
            Integer status = preorder.getInteger("status");
            List<MoreProduct> list = moreProductRepository.
                    findAllByServiceIdAndAddition(2,
                            String.valueOf(status==null ?
                                    -1:status));
            for(int j=0;j<list.size();j++){
                MoreProduct moreProduct = list.get(j);
                sum+=moreProduct.getSum();
            }
        }

        return sum;

    }

    @Override
    public Page<Order> getOrderByUserId(String user_id, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Order> orders=orderRepository.findAllByUserIdAndAbledTrue(user_id,pageable);
        for(Iterator<Order> i=orders.iterator();i.hasNext();){
            Order order=i.next();
            List<Preorder> preorders=preorderService.getAllPreorderByOrderId(order.getOrderId());
            if(preorders.size()==0) {
                i.remove();
                continue;
            }
            order.setPreorder(preorders);
        }
        return orders;
    }


    /**
     * 完成支付后预付单的特异性操作
     * @param order_id
     * @return
     */

    @Value("${express.card.product-id}")
    String addressCardProductId;

    @Autowired
    WexinTokenService wexinTokenService;

    public boolean finishIt(String order_id){
        List<Preorder> preorders=preorderRepository.findAllByOrderId(order_id);
        if(preorders==null||preorders.size()==0) return false;
        Order order=orderRepository.findByOrderId(order_id);
        User user=userRepository.findByUserId(order.getUserId());
        boolean ret=true;
        for(int i=0;i<preorders.size();i++){
            Preorder preorder=preorders.get(i);
            MoreProduct moreProduct=moreProductRepository.findFirstByProductId(preorder.getProductId());
            if(preorder.getServiceId()==1) {          //快递预付单的支付完成办法
                wexinTokenService.pushFinishPayed(order.getPrepayId(),moreProduct.getProductName(),
                        order.getTotalFee(),order.getTime(),order.getOrderId(),user.getOpenId());
            }else if(preorder.getServiceId()==2){
                ret=userProductService.finishPayed(preorder);
            }else if(preorder.getServiceId()==9){       //快递代取月卡购买预付单的支付完成办法
               // wexinTokenService.pushFinishPayed(order.getPrepayId(),moreProduct.getProductName(),
                //        order.getTotalFee(),order.getTime(),order.getOrderId(),user.getOpenId()); //推送
                CardType cardType = cardTypeRepository.findFirstByMoreProductId(preorder.getProductId());
                ret = cardService.reNew(order.getUserId(),cardType.getCardTypeId());
            }
        }
        if(!ret) {
            //此处处理失败的情况
            throw new RuntimeException("fail to finish pay");
        }else
            return true;
    }

    /**
     * 用于controller层获取总价
     * @param data
     * @return
     */

    @Override
    public JSONObject getTotal(JSONObject data){
        JSONArray preordersJson=data.getJSONArray("preorders");
        JSONObject toret = new JSONObject();
        toret.put("total",this.calcuTotal(preordersJson));
        toret.put("status",true);
        JSONArray extra = new JSONArray();
        for(int i=0;i<preordersJson.size();i++){
            JSONObject preorder = preordersJson.getJSONObject(i);
            if(preorder.getInteger("service_id")==2){
                List<MoreProduct> moreProducts = moreProductRepository.
                        findAllByServiceIdAndAddition(2,String.valueOf(preorder.getInteger("status")));
                for(int j=0;j<moreProducts.size();j++){
                    MoreProduct moreProduct = moreProducts.get(j);
                    extra.add(new JSONObject(){
                        {
                            this.put("name",moreProduct.getProductName());
                            this.put("price",moreProduct.getSum());
                        }
                    });
                }
            }
        }
        toret.put("extra",extra);
        return toret;
    }
}
