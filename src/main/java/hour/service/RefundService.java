package hour.service;

import hour.model.Refund;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RefundService {

    Long getCount();

    Page<Refund> getRefund(int page, int size);
}
