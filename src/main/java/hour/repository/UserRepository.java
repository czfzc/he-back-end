package hour.repository;

import hour.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByOpenIdAndAbledTrue(String openid);
    User findByMysession(String mysession);
    User findByUserId(String user_id);
}
