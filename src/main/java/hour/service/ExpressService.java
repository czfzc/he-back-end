package hour.service;

import com.alibaba.fastjson.JSONArray;
import hour.model.Express;
import hour.model.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ExpressService {
    boolean addExpress(JSONArray express,String preorder_id,String address_id,String user_id);

    List<Express> getExpress(String preorder_id, Integer page, Integer size);

    Page<Express> getAllExpress(Integer page, Integer size);

    Double getTotalPrice(String preorder_id);

    Page<Express> searchExpressById(String value, Integer page, Integer size);

    Page<Express> searchExpressByUserId(String value, Integer page, Integer size);

    Page<Express> getExpressByExpressPoint(String express_point_id, Integer page, Integer size);
}
