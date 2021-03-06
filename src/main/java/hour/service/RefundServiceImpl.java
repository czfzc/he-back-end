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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

import static hour.util.NetUtil.postData;
import static hour.util.StringUtil.*;

@Service("RefundService")
public class RefundServiceImpl implements RefundService {
    /**
     * 获取总订单数
     *
     * @return
     */
    @Autowired
    EntityManager entityManager;

    @Autowired
    RefundRepository refundRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Long getCount() {
        String sql = "select count(a.orderId) from Refund a";
        Query query = entityManager.createQuery(sql);
        return (Long) query.getSingleResult();
    }

    @Override
    public Page<Refund> getRefund(int page, int size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        return refundRepository.findAll(pageable);
    }

    @Override
    public String userRefundOrder(String user_id, String order_id, String reason) {
        if (user_id == null) return createStatus(false);
        Order order = orderRepository.findByOrderId(order_id);
        if (order == null) return createStatus(false);
        if (order.getPayed() != 1) return createStatus(false);
        if (!user_id.equals(order.getUserId())) return createStatus(false);
        Refund refund = new Refund();
        refund.setOrderId(order_id);
        refund.setUserId(user_id);
        refund.setTime(new Date());
        refund.setReason(reason);
        refund.setAbled(true);
        refund.setPayed(1);
        refundRepository.save(refund);
        order.setPayed(3);     //order的payed字段：4是被拒绝 3是等待退款 2是退款成功 1是已付款 0是未付款
        // order的payed和refund的级联
        orderRepository.save(order);
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
     *
     * @return
     */
    @Value("${wexin.cert}")
    String cert;

    private boolean refundOrder(String orderid, String refundid) {

        Order order = orderRepository.findByOrderId(orderid);

        if (order == null)
            return false;

        if (order.getPayed() == 4 || order.getPayed() == 2 || order.getPayed() == 0)
            return false;

        Double total = order.getTotalFee();

        if (total == 0) {           //假如没花钱
            return true;
        }


        Map<String, String> content = new LinkedHashMap<String, String>();
        content.put("!appid", appid);                                                                                        //小程序appid
        content.put("!mch_id", mch_id);                                                                                        //商户id
        content.put("!nonce_str", getRandom(10));                                                                                //随机数 不超过32位
        //	content.put("!notify_url", "https://shop.wly01.cn/myshop/onFinishedPayed");											//退款完成的会调接口
        content.put("!out_trade_no", orderid);                                                                                //订单id
        content.put("!out_refund_no", refundid);                                                                                //退款id
        content.put("!total_fee", String.valueOf((int) (total * 100)));                                                        //订单总价
        content.put("!refund_fee", String.valueOf((int) (total * 100)));                                                        //退款金额
        //	content.put("refund_desc", "sold_out");																				//退款原因
        content.put("!sign", StringUtil.calculateSign(content, mykey));
        String xml = xmlCreater(content);
        System.out.println(xml);
        try {
            Resource res = new ClassPathResource(cert);
            System.out.println("cerpath:  ");
            String refund = postData("https://api.mch.weixin.qq.com/secapi/pay/refund", xml, mch_id, res.getInputStream());
            System.out.println(new String(refund.getBytes(), "UTF-8"));
            if (refund.contains("FAIL"))
                return false;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean acceptRefund(String refund_id) {
        Refund refund = refundRepository.findByRefundId(refund_id);
        if (refund == null) return false;
        Order order = orderRepository.findByOrderId(refund.getOrderId());

        if (refund.getPayed() == 3) {

            if (!this.refundOrder(refund.getOrderId(), refund_id)) return false;

            order.setPayed(2);  //payed为2则为退款成功

            return (orderRepository.save(order).getPayed() == 2);

        } else return false;
    }

    @Override
    public boolean refuseRefund(String refund_id) {

        Refund refund = refundRepository.findByRefundId(refund_id);
        if (refund == null) return false;

        Order order = orderRepository.findByOrderId(refund.getOrderId());
        if (order == null) return false;

        if (refund.getPayed() == 3) {
            order.setPayed(4);      //四代表被拒绝
        } else return false;

        return (orderRepository.save(order).getPayed() == 4);
    }

    @Override
    public boolean adminRefund(String order_id) {
        //   Refund refund=refundRepository.findByOrderId(order_id);
        Refund refund = new Refund();
        Order order = orderRepository.findByOrderId(order_id);
        if (order == null) return false;
        refund.setAbled(true);

        refund.setOrderId(order_id);
        refund.setUserId(order.getUserId());
        refund.setTime(new Date());
        refund.setReason("管理员主动退款");
        refund.setAbled(true);
        refund.setPayed(1);
        refundRepository.save(refund);

        if (!this.refundOrder(order_id, refund.getRefundId())) return false;
        order.setPayed(2);
        return orderRepository.save(order).getPayed() == 2;
    }

}
