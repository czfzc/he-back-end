package hour.repository;

import hour.model.Preorder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreorderRepository extends JpaRepository<Preorder,String> {

    List<Preorder> findAllByOrderId(String order_id);

    Page<Preorder> findAllByIdContaining(String value, Pageable pageable);

    Page<Preorder> findAllByUserIdContaining(String value, Pageable pageable);

}
