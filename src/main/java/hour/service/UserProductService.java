package hour.service;

import com.alibaba.fastjson.JSONArray;
import hour.model.UserProduct;

import java.util.Date;
import java.util.List;

public interface UserProductService {

    boolean addUserProducts(JSONArray jsonArray, String preorder_id, String userid, Date time);

    double getTotalPrice(String preorder_id);

    List<UserProduct> getUserProducts(String preorderId);
}
