package hour.service;

import com.alibaba.fastjson.JSONArray;
import hour.model.Preorder;

import java.util.List;

public interface PreorderService {
    public double calculateTotal(String order_id);

    List<Preorder> getPreorder(String order_id);

    public boolean preorderIt(JSONArray arr, String order_id, String user_id);
}
