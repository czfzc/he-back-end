package hour.service;

import hour.model.Order;
import hour.model.Refund;
import hour.repository.OrderRepository;
import hour.repository.RefundRepository;
import hour.util.StringUtil;
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
import static hour.util.NetUtil.postData;
import static hour.util.StringUtil.*;

@Service("RefundService")
public class RefundServiceImpl implements RefundService{
    /**
     * 获取总订单数
     * @return
     */
    @Autowired
    EntityManager entityManager;

    @Autowired
    RefundRepository refundRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Long getCount(){
        String sql="select count(a.orderId) from Refund a";
        Query query = entityManager.createQuery(sql);
        return (Long)query.getSingleResult();
    }

    @Override
    public Page<Refund> getRefund(int page, int size){
        Pageable pageable=new PageRequest(page, size, Sort.Direction.DESC, "time");
        return refundRepository.findAll(pageable);
    }

    @Override
    public String adminRefundOrder(String order_id){
        Order order=orderRepository.findByOrderId(order_id);
        if(order==null)
            return createStatus(false);
        if(!this.refundOrder(order_id)){
            return createStatus(false);
        }
        order.setPayed(2);  //payed为2则为退款成功
        orderRepository.save(order);
        return createStatus(true);
    }

    @Override
    public String userRefundOrder(String user_id,String order_id, String reason){
        if(user_id==null) return createStatus(false);
        Order order=orderRepository.findByOrderId(order_id);
        if(order==null) return  createStatus(false);
        if(order.getPayed()!=1) return createStatus(false);
        if(!user_id.equals(order.getUserId())) return createStatus(false);
        Refund refund=new Refund();
        String refund_id= UUID.randomUUID().toString().replace("-","");
        refund.setRefundId(refund_id);
        refund.setOrderId(order_id);
        refund.setUserId(user_id);
        refund.setTime(new Date());
        refund.setReason(reason);
        refund.setRefused(false);
        refund.setAbled(true);
        refund.setSucceed(false);

        order.setPayed(3);     //order的payed字段： 3是等待退款 2是退款成功 1是已付款 0是未付款

        refundRepository.save(refund);

        //此处应该通知管理员处理

        return createStatus(true);
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


    /**
     * 退款
     * @return
     */
    private boolean refundOrder(String orderid){

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
        content.put("!sign", StringUtil.calculateSign(content,mykey));
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
        order.setPayed(2);
        orderRepository.save(order);
        return true;
    }




}
