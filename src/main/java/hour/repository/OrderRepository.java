package hour.repository;

import hour.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository  extends JpaRepository<Order,Integer> {
    Order findByOrderIdAndUserId(String order_id,String user_id);
    List<Order> findAllByUserId(String user_id);

}
