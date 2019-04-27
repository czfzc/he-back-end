package hour.service;

public interface AdminService {
    String login(String admin_id, String raw_password);

    String regist(String admin_id, String raw_password, Integer sms_code, String name);

    String send(String admin_id);

    String getAdminId(String session_key);
}
