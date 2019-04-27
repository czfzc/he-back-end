package hour.repository;

import hour.model.Express;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpressRepository extends JpaRepository<Express,Integer> {
    List<Express> findAllByPreorderIdAndAbledTrue(String preorder_id);
    List<Express> findAllByAbledTrue();
    Express findFirstByExpressId(String express_id);
}
