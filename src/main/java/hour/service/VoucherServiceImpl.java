package hour.service;

import hour.model.Voucher;
import hour.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static hour.util.StringUtil.createStatus;

@Service("VoucherService")
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    VoucherRepository voucherRepository;

    String varifyVoucher(String user_id,String voucher_id,String type_id){
        Voucher voucher=voucherRepository.
                findFirstByCardIdAndUsedFalseAndAbledTrueAndCheckUserIdIsNull(voucher_id);
        if(voucher==null) return createStatus(false);
        voucher.setCheckUserId(user_id);
        voucherRepository.save(voucher);
        return createStatus(true);
    }

    HashMap getVoucher(String user_id){
        return new HashMap(){
            {
                this.put("status",true);
                this.put("count",voucherRepository.
                        countAllByCheckUserIdAndAbledTrueAndUsedFalseAndCheckUserIdNotNull(user_id));
            }
        };
    }

    boolean useVoucher(String user_id){
        Voucher voucher=voucherRepository.
                findFirstByCheckUserIdAndAbledTrueAndUsedFalseAndCheckUserIdNotNull(user_id);
        if(voucher==null) return false;
        voucher.setUsed(true);
        voucherRepository.save(voucher);
        return true;
    }
}
