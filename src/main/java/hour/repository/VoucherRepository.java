package hour.repository;

import hour.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher,Integer> {
    Voucher findFirstByCardIdAndUsedFalseAndAbledTrueAndCheckUserIdIsNull(String card_id);
    List<Voucher> findAllByCheckUserIdAndAbledTrueAndUsedFalseAndCheckUserIdNotNull(String user_id);
    Long countAllByCheckUserIdAndAbledTrueAndUsedFalseAndCheckUserIdNotNull(String user_id);
    Voucher findFirstByCheckUserIdAndAbledTrueAndUsedFalseAndCheckUserIdNotNull(String user_id);
}
