package hour.service;

import com.alibaba.fastjson.JSONArray;

public interface PreorderService {
    public double calculateTotal(String order_id);
    public boolean preorderIt(JSONArray arr,String order_id,String user_id);
}
