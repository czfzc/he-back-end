package hour.repository;

import hour.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order,String>, JpaSpecificationExecutor<Order> {
    Order findByOrderIdAndUserId(String order_id,String user_id);
    Page<Order> findAllByUserId(String user_id,Pageable pageable);
    Order findByOrderId(String order_id);
    Page<Order> findAllByOrderIdContaining(String value, Pageable pageable);
    Page<Order> findAllByUserIdContaining(String value, Pageable pageable);
}
