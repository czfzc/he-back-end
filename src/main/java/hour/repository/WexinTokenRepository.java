package hour.repository;

import hour.model.WexinToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WexinTokenRepository extends JpaRepository<WexinToken,String> {
    WexinToken findFirstByAppid(String appid);
}
