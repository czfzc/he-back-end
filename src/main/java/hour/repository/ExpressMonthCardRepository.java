package hour.repository;

import hour.model.ExpressMonthCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpressMonthCardRepository extends JpaRepository<ExpressMonthCard,Integer> {
    List<ExpressMonthCard> findByUserIdAndPayedTrueAndAbledTrue(String userid);

}
