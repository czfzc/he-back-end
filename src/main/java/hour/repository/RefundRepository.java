package hour.repository;

import hour.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundRepository extends JpaRepository<Refund,String> {
    List<Refund> findAllByAbledTrue();
    Refund findByRefundId(String refund_id);
}
