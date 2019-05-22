package hour.repository;

import hour.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findByOpenId(String openid);
    User findByMysessionAndAbledTrue(String mysession);
    User findByUserId(String user_id);
}
