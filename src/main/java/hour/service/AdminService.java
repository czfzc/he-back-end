package hour.service;

import com.alibaba.fastjson.JSONObject;

public interface AdminService {
    String login(String admin_id, String raw_password);

    String regist(String admin_id, String raw_password, Integer sms_code, String name);

    boolean validateSession(String session_key);

 /*   String send(String admin_id);*/

    String getAdminId(String session_key);

    JSONObject getCredentials();
}
