package hour.service;

import hour.model.ExpressMonthCard;
import hour.repository.ExpressMonthCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service("ExpressMonthCardService")
public class ExpressMonthCardServiceImpl implements ExpressMonthCardService {

    @Autowired
    ExpressMonthCardRepository expressMonthCardRepository;

    @Override
    public boolean isAbled(ExpressMonthCard expressMonthCard){
        if(expressMonthCard==null) return false;
        if(!expressMonthCard.isAbled()) return false;
        if(!expressMonthCard.isPayed()) return false;
        Date date1=expressMonthCard.getEndTime();
        Date date2=new Date();
        if(date2.compareTo(date1)>0) return false;
        return true;
    }

    @Override
    public boolean payIt(String user_id,String preorder_id){
        ExpressMonthCard expressMonthCard=expressMonthCardRepository.
                findFirstByUserIdAndPayedTrueAndAbledTrue(user_id);
        if(expressMonthCard!=null) return false;
        if(this.isAbled(expressMonthCard)) return false;
        expressMonthCard=new ExpressMonthCard();
        expressMonthCard.setCardId(UUID.randomUUID().toString().replace("-",""));
        expressMonthCard.setUserId(user_id);
        expressMonthCard.setServiceId(1);
        expressMonthCard.setProductId("09201921");
        expressMonthCard.setAbled(true);
        expressMonthCard.setPayed(false);
        expressMonthCard.setPreorderId(preorder_id);

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.MONTH, 1);
        Date dt1 = rightNow.getTime();

        expressMonthCard.setEndTime(dt1);

        expressMonthCardRepository.save(expressMonthCard);
        return true;
    }

}
