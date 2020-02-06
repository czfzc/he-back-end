package hour.service;

import com.alibaba.fastjson.JSONObject;
import hour.model.RcmdCard;


import java.util.List;

public interface RcmdCardService {

    List<RcmdCard> getAllRcmdCardByBuildingId(String building_id);

    List<RcmdCard> getAllRcmdCard();

    boolean delRcmdCardById(String id);

    boolean addRcmdCard(JSONObject jsonObject);

    boolean updateRcmdCard(JSONObject jsonObject);
}
