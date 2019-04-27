package hour.repository;

import hour.model.Preorder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreorderRepository extends JpaRepository<Preorder,Integer> {
    List<Preorder> findAllByOrderId(String order_id);
    Preorder findById(String preorder_id);
    List<Preorder> findAllByOrderIdAndStatus(String order_id,Integer status);
}
