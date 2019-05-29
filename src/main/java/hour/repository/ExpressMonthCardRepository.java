package hour.repository;

import hour.model.ExpressMonthCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpressMonthCardRepository extends JpaRepository<ExpressMonthCard,String> {
    ExpressMonthCard findFirstByUserIdAndAbledTrue(String user_id);
    ExpressMonthCard findFirstByUserId(String user_id);
}
