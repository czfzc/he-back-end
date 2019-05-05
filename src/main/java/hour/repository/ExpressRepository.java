package hour.repository;

import hour.model.Express;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpressRepository extends JpaRepository<Express,Integer> {
    Page<Express> findAllByPreorderIdAndAbledTrue(String preorder_id, Pageable pageable);
    List<Express> findAllByAbledTrue();
    Express findFirstByExpressId(String express_id);
    List<Express> findAllByPreorderId(String preorderid);
}
