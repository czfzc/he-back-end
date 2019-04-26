package hour.service;

import com.alibaba.fastjson.JSONArray;

public interface ExpressService {
    public boolean addExpress(JSONArray express,String preorder_id,String address_id);
    public Double getTotalPrice(String preorder_id);
}
