package hour.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public interface WexinTokenService {
    String getAccessToken(String appid,String appkey);

    JSONObject getInfoByOpenid(String openid, String appid, String appkey);

    boolean pushFinishPayed(String prepay_id, String name, Double price, Date time,
                            String order_id, String openid);

    boolean pushWithdrawExpressInfo(String express_id);

    boolean pushSendedExpressInfo(String express_id);
}
