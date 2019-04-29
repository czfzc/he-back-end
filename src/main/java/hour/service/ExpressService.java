package hour.service;

import com.alibaba.fastjson.JSONArray;
import hour.model.Express;

import java.util.List;

public interface ExpressService {
    boolean addExpress(JSONArray express,String preorder_id,String address_id,String user_id);

    List<Express> getExpress(String preorder_id, Integer page, Integer size);

    List<Express> getAllExpress(Integer page, Integer size);

    Double getTotalPrice(String preorder_id);
}
