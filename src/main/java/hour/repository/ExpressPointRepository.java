package hour.repository;

import hour.model.ExpressPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpressPointRepository extends JpaRepository<ExpressPoint,Integer> {
    ExpressPoint findFirstByExpressPointId(String express_point_id);
    ExpressPoint findFirstByName(String name);
}
