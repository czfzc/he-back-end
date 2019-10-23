package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {
    /**
     * 用户下总单
     * @return
     */

    String payOrder(String ip, String mysession, JSONArray preorders);

    @Transactional(rollbackFor = Exception.class)
    String cardPayOrder(String user_id, String card_type_id);

    String repayOrder(String user_id, String order_id);

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

    Order getOrderById(String orderId);

    Long getCount();

    Page<Order> searchOrderById(String value, Integer page, Integer size);

    Page<Order> searchOrderByUserId(String value, Integer page, Integer size);


    double calcuTotal(JSONArray preorders);

    Page<Order> getOrderByUserId(String user_id, Integer page, Integer size);

    JSONObject getTotal(JSONObject data);
}
