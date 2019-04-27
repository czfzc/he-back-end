package hour.service;

import com.alibaba.fastjson.JSONArray;
import hour.model.Express;

import java.util.List;

public interface ExpressService {
    public boolean addExpress(JSONArray express,String preorder_id,String address_id);

    List<Express> getExpress(String preorder_id);

    public Double getTotalPrice(String preorder_id);
}
