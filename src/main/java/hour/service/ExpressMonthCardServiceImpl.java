package hour.service;

import hour.model.ExpressMonthCard;
import hour.repository.ExpressMonthCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static hour.util.StringUtil.createStatus;

@Service("ExpressMonthCardService")
public class ExpressMonthCardServiceImpl implements ExpressMonthCardService {

    @Autowired
    ExpressMonthCardRepository expressMonthCardRepository;

    @Override
    public boolean isAbled(ExpressMonthCard expressMonthCard) {
        if (expressMonthCard == null) return false;
        Date date1 = expressMonthCard.getEndTime();
        Date date2 = new Date();
        if (date2.compareTo(date1) > 0) return false;
        return true;
    }

    @Override
    public boolean isAbled(String user_id) {
        return this.getRemainsTime(user_id)>0;
    }

    @Override
    public boolean payIt(String user_id, String preorder_id) {
        ExpressMonthCard expressMonthCard = new ExpressMonthCard();

        expressMonthCard.setCardId(UUID.randomUUID().toString().replace("-", ""));
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

    @Override
    public int getRemainsTime(String user_id) {
        int sum=0;
        List<ExpressMonthCard> cards = expressMonthCardRepository.
                findByUserIdAndPayedTrueAndAbledTrue(user_id);
        if (cards == null) return 0;
        for(int i=0;i<cards.size();i++){
            ExpressMonthCard expressMonthCard=cards.get(i);
            if (isAbled(expressMonthCard)) {
                Date date1 = new Date();
                Date date2 = expressMonthCard.getEndTime();
                int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
                if(days>0)
                    sum+=days;
            }
        }
        return sum;
    }

}
