package hour.repository;

import hour.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Column;

public interface OrderRepository  extends JpaRepository<Order,Integer> {
    Order findByOrderIdAndUserId(String order_id,String user_id);
}
