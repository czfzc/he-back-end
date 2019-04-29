package hour.repository;

import hour.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order,Integer>, JpaSpecificationExecutor<Order> {
    Order findByOrderIdAndUserId(String order_id,String user_id);
    List<Order> findAllByUserId(String user_id);
    Order findByOrderId(String order_id);
}
