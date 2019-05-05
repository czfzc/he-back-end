package hour.service;

import com.alibaba.fastjson.JSONArray;
import hour.model.Preorder;

import java.util.List;

public interface PreorderService {

    double calculateTotal(String order_id);

    List<Preorder> getAllPreorderByOrderId(String order_id);

    List<Preorder> getAllPreorder(Integer page, Integer size);

    List<Preorder> getExpressPreorder(Integer page, Integer size);

    Long getExpressCount();

    boolean preorderIt(JSONArray arr, String order_id, String user_id);
}
