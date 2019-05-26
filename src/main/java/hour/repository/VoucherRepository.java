package hour.repository;

import hour.model.Voucher;
import hour.model.VoucherGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher,String> {
    Voucher findFirstByCardIdAndUsedFalseAndAbledTrueAndCheckUserIdIsNull(String card_id);
    List<Voucher> findAllByCheckUserIdAndAbledTrueAndUsedFalseAndCheckUserIdNotNull(String user_id);
    Long countAllByCheckUserIdAndAbledTrueAndUsedFalseAndCheckUserIdNotNull(String user_id);
    Voucher findFirstByCheckUserIdAndTypeIdAndAbledTrueAndUsedFalseAndCheckUserIdNotNull(String user_id, String type_id);
}
