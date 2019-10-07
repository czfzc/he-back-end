package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.Preorder;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PreorderService {

    double calculateTotal(String order_id);

    List<Preorder> getAllPreorderByOrderId(String order_id);

    Page<Preorder> getAllPreorder(Integer page, Integer size);

    boolean preorderIt(JSONArray arr, String order_id, String user_id);

    Page<Preorder> searchPreorderById(String value, Integer page, Integer size);

    Page<Preorder> searchPreorderByUserId(String value, Integer page, Integer size);

    double cacuExpressTotalByObject(JSONObject preorder);

    Page<Preorder> getProductSendPreorderByBuildingId(String building_id, Integer page, Integer size);

    Page<Preorder> getProductWithdrawPreorderByBuildingId(String building_id, Integer page, Integer size);
}
