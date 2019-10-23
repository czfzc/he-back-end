package hour.repository;

import hour.model.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTypeRepository extends JpaRepository<CardType,String> {
    CardType findFirstByMoreProductId(String more_product_id);
}
