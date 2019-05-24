package hour.service;

import hour.model.Refund;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RefundService {

    Long getCount();

    Page<Refund> getRefund(int page, int size);

    String userRefundOrder(String user_id,String order_id, String reason);

    boolean acceptRefund(String refund_id);

    boolean refuseRefund(String refund_id);

    boolean adminRefund(String order_id);
}
