package hour.repository;

import hour.model.RcmdCard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RcmdCardRepository extends JpaRepository<RcmdCard,String> {
    List<RcmdCard> findAllByAbledTrueAndVisibleTrueOrderByTimeDesc();
    List<RcmdCard> findAllByBuildingIdAndAbledTrueAndVisibleTrueOrderByTimeDesc(String building_id);
    List<RcmdCard> findAllByOrderByTimeDesc();
}
