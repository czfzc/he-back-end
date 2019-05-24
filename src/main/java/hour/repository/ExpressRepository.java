package hour.repository;

import hour.model.Express;
import hour.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpressRepository extends JpaRepository<Express,String> {
    Page<Express> findAllByPreorderIdAndAbledTrue(String preorder_id, Pageable pageable);

    Express findFirstByExpressId(String express_id);

    List<Express> findAllByPreorderId(String preorderid);

    Page<Express> findAllByExpressIdContaining(String value, Pageable pageable);

    Page<Express> findAllByUserIdContaining(String value, Pageable pageable);

    Page<Express> findAllByExpressPointId(String express_point_id,Pageable pageable);
}
