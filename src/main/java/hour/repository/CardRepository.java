package hour.repository;

import hour.model.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<UserCard,String> {
    UserCard findFirstByUserIdAndCardTypeId(String user_id,String card_type_id);
    UserCard findFirstByUserIdAndCardIdAndAbledTrue(String user_id,String card_id);
}
