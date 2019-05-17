package hour.service;

import hour.model.Voucher;
import hour.model.VoucherGroup;
import hour.repository.VoucherGroupRepository;
import hour.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

import static hour.util.StringUtil.createStatus;

@Service("VoucherService")
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherGroupRepository voucherGroupRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public String varifyVoucher(String user_id, String card_id){
        Voucher voucher=voucherRepository.
                findFirstByCardIdAndUsedFalseAndAbledTrueAndCheckUserIdIsNull(card_id);
        if(voucher==null) return createStatus(false);
        voucher.setCheckUserId(user_id);
        voucherRepository.save(voucher);
        return createStatus(true);
    }

    @Override
    public List<VoucherGroup> getVoucher(String user_id){
        return voucherGroupRepository.list(user_id);
    }

    @Override
    public boolean useVoucher(String user_id, String type_id){  //用户使用一张指定类型的代金卷
        Voucher voucher=voucherRepository.
                findFirstByCheckUserIdAndTypeIdAndAbledTrueAndUsedFalseAndCheckUserIdNotNull(user_id,type_id);
        if(voucher==null) return false;
        voucher.setUsed(true);
        voucherRepository.save(voucher);
        return true;
    }

  /*  private List getVoucherDAO(String user_id){
        String sql="SELECT\n" +
                "\tcount( a.type_id ) as count,\n" +
                "\ta.type_id,\n" +
                "\tany_value ( a.check_user_id ) as check_user_id,\n" +
                "\tany_value ( a.service_id ) as service_id,\n" +
                "\tany_value(b.name) as name\n" +
                "FROM\n" +
                "\tuser_voucher  a\n" +
                "\tINNER JOIN user_voucher_type b\n" +
                "\ton b.type_id=a.type_id\n" +
                "\tWHERE a.check_user_id =? and a.abled=1 and a.used=0\n" +
                "GROUP BY\n" +
                "\ttype_id";
        Query query=entityManager.createQuery(sql);
        query.setParameter(0,user_id);
        return query.getResultList();
    }*/
}

