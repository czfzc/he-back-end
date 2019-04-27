package hour.service;

import com.alibaba.fastjson.JSONArray;
import hour.model.Order;

import java.util.List;

public interface OrderService {
    /**
     * 用户下总单
     * @return
     */

    String payOrder(String ip, String mysession, JSONArray preorders);

    /**
     * 根据微信回调接口的发送来的xml设置订单付款
     * @param xml
     * @return
     */

    boolean onFinishPayed(String xml);


    /**
     * 获取所有订单
     * @param mysession
     * @return
     */

    List<Order> getOrder(String mysession);

    /**
     * 退款
     */
    boolean refundOrder(String orderid);
}
