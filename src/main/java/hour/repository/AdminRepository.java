package hour.repository;

import hour.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findFirstByAdminIdAndPasswordAndAbledTrue(String admin_id, String password);
    Admin findFirstByAdminIdAndSmsCodeAndAbledTrue(String admin_id,Integer sms_code);
    Admin findFirstBySessionKey(String session_key);
    Admin findFirstByAdminId(String admin_id);
}
