package hour.service;

import hour.model.Voucher;
import hour.model.VoucherGroup;

import java.util.List;

public interface VoucherService{

    String varifyVoucher(String user_id, String card_id);

    List<VoucherGroup> getVoucher(String user_id);

    boolean useVoucher(String user_id, String type_id);
}
