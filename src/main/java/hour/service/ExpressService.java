package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.Express;
import hour.model.Order;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface ExpressService {
    boolean addExpress(JSONArray express,String preorder_id,String address_id,String user_id,String send_method_id);

    List<Express> getExpress(String preorder_id);

    HashMap getAllExpressByPayed(Integer page, Integer size);

    Double getTotalPrice(String preorder_id);

    Page<Express> searchExpressById(String value, Integer page, Integer size);

    Page<Express> searchExpressByUserId(String value, Integer page, Integer size);

    HashMap getExpressByExpressPointAndPayed(String express_point_id, Integer page, Integer size);

    double getTotalByObject(JSONObject express);

    long countTotalExpressByPayed();

    long countTotalExpressByPointAndPayed(String express_point_id);

    String getPrepayIdByExpressId(String express_id);
}
