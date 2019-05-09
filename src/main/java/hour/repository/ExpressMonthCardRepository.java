package hour.repository;

import hour.model.ExpressMonthCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpressMonthCardRepository extends JpaRepository<ExpressMonthCard,Integer> {
    ExpressMonthCard findFirstByUserIdAndPayedTrueAnAndAbledTrue(String userid);
}
