package hour.service;

import com.alibaba.fastjson.JSONArray;
import hour.model.Order;
import org.springframework.data.domain.Page;

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
     * 获取订单
     * @param page 页数
     * @param size 每页大小
     * @return
     */

    Page<Order> getOrder(Integer page, Integer size);

    /**
     * 退款
     */
    boolean refundOrder(String orderid);

    Long getCount();

    Page<Order> searchOrderById(String value, Integer page, Integer size);

    Page<Order> searchOrderByUserId(String value, Integer page, Integer size);


    double calcuToal(JSONArray preorders);
}
