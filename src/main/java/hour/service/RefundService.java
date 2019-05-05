package hour.service;

import hour.model.Refund;

import java.util.List;

public interface RefundService {

    Long getCount();

    List<Refund> getRefund(int page, int size);
}
