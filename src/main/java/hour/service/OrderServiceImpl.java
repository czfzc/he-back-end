package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.Util.NetUtil;
import hour.model.Order;
import hour.model.User;
import hour.repository.OrderRepository;
import hour.repository.UserRepository;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static hour.Util.CodeUtil.md5;
import static hour.Util.StringUtil.createStatus;
import static hour.Util.StringUtil.xmlCreater;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PreorderService preorderService;

    @Override
    public String payOrder(String ip, String mysession,JSONArray preorders){
        User user=userRepository.findByMysession(mysession);
        String open_id=user.getOpenId();
        String user_id=user.getUserId();
        String order_id=UUID.randomUUID().toString().replace("-","");

        Order order=new Order();

        order.setAbled(1);
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
            return this.unifiedorder(order_id,ip,open_id,(int)total*100).toJSONString();
        }

        return createStatus(false);

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

    String mch_id="1527312851";
    String appid="wxf3bea9542d72b8de";
    String mykey="32efb0ecf01749c8854ac34d04d998fc";

    private JSONObject unifiedorder(String orderid, String ip, String openid, Integer total){
        String nonce_str= UUID.randomUUID().toString().replace("-", "");
        Map<String,String> content=new LinkedHashMap<String,String>();
        content.put("!appid", appid);																						//小程序appid
        content.put("!body", "express");																					//商品描述
        content.put("!mch_id", mch_id);																						//商户id
        content.put("!nonce_str", nonce_str);																				//随机数 不超过32位
        content.put("!notify_url", "https://shop.wly01.cn/myshop/onFinishPayed");													//订单完成的会调接口
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
        toret.put("timeStamp", String.valueOf(System.currentTimeMillis()));
        toret.put("appId", appid);
        toret.put("nonceStr", UUID.randomUUID().toString().replace("-", ""));
        toret.put("signType", "MD5");
        try {
            Document doc= DocumentHelper.parseText(xml);
            Element ele=doc.getRootElement();
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


}
