package hour.service;

import com.alibaba.fastjson.JSONArray;

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


}
