package hour.service;

import hour.model.User;
import hour.model.Voucher;
import hour.model.VoucherGroup;
import hour.repository.UserRepository;
import hour.repository.VoucherGroupRepository;
import hour.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import java.util.Date;
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

    @Autowired
    UserRepository userRepository;

    @Override
    public String varifyVoucher(String user_id, String card_id){
        Voucher voucher=voucherRepository.
                findFirstByCardIdAndUsedFalseAndAbledTrueAndUserMainIdIsNull(card_id);
        User user=userRepository.findByUserId(user_id);
        if(user==null) return createStatus(false);
        if(voucher==null) return createStatus(false);
        voucher.setUserMainId(user.getMainId());
        voucherRepository.save(voucher);
        return createStatus(true);
    }

    @Override
    public List<VoucherGroup> getVoucher(String main_id){
        return voucherGroupRepository.list(main_id);
    }

    @Override
    public boolean useVoucher(String user_main_id, String type_id){  //用户使用一张指定类型的代金卷
        Voucher voucher=voucherRepository.
                findFirstByUserMainIdAndTypeIdAndAbledTrueAndUsedFalse(user_main_id,type_id);
        if(voucher==null) return false;
        voucher.setUsed(true);
        voucherRepository.save(voucher);
        System.out.println("使用代金卷成功");
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

    @Value("${express.voucher-type-id}")
    String expressVoucherTypeId;

    @Override
    public boolean getMoreExpressVoucher(String union_id,int num){
        User user=userRepository.findByUnionId(union_id);
        if(user==null) return false;
        if(user.isVoucherGeted()) return false;
        for(int i=0;i<num;i++){
            Voucher voucher=new Voucher();
            voucher.setTypeId(expressVoucherTypeId);
            voucher.setName("快递代取劵");
            voucher.setAbled(true);
            voucher.setUsed(false);
            voucher.setCheckTime(new Date());
            voucher.setUserMainId(user.getMainId());
            voucher.setServiceId(1);
            if(voucherRepository.save(voucher).getCardId()==null)
                return false;
        }
        user.setVoucherGeted(true);
        return userRepository.save(user).isVoucherGeted();
    }
}

