package hour.repository;

import hour.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findFirstByAdminIdAndPassword(String admin_id, String password);
    Admin findFirstByAdminIdAndSmsCode(String admin_id,Integer sms_code);
}
