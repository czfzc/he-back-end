package hour.service;

import java.util.Date;

public interface WexinTokenService {
    String getAccessToken(String appid,String appkey);

    String getUnionidByOpenid(String openid, String appid, String appkey);

    boolean pushFinishPayed(String prepay_id, String name, Double price, Date time,
                            String order_id, String openid, String appid, String appkey);
}
