package hour.repository;

import hour.model.Voucher;
import hour.model.VoucherGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher,String> {

    Voucher findFirstByUserMainIdAndTypeIdAndAbledTrueAndUsedFalse(String user_main_id, String type_id);

    Voucher findFirstByCardIdAndUsedFalseAndAbledTrueAndUserMainIdIsNull(String card_id);
}
