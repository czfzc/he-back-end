package hour.service;

import hour.model.ExpressMonthCard;
import hour.repository.ExpressMonthCardRepository;
import hour.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        return this.getRemainsTime(expressMonthCard.getUserId())>0;
    }

    @Override
    public boolean isAbled(String user_id) {
        return this.getRemainsTime(user_id)>0;
    }

    @Override
    public int getRemainsTime(String user_id) {
        int sum=0;
        ExpressMonthCard card = expressMonthCardRepository.
                findFirstByUserIdAndAbledTrue(user_id);
        if (card == null) return 0;
        int last=(int)TimeUtil.getTimeDiffDate(card.getEndTime(),new Date());
        return last<0?0:last;
    }

    @Value("${address.card.renew-add-day}")
    int renew_add_day;

    /**
     * 续费快递代取会员卡
     * @return
     */
    @Override
    public boolean reNew(String user_id) {
        ExpressMonthCard card = expressMonthCardRepository.
                findFirstByUserIdAndAbledTrue(user_id);
        if(card==null){         //假如用户没有卡
            Date date=TimeUtil.addDay(new Date(),renew_add_day);
            card=new ExpressMonthCard();
            card.setUserId(user_id);
            card.setServiceId(1);
            card.setProductId("09201921");
            card.setAbled(true);
            card.setEndTime(date);
            card.setUseTimes(0);
            Date date2=expressMonthCardRepository.save(card).getEndTime();
            return date.compareTo(date2)==0;
        }else if(this.getRemainsTime(user_id)==0){   //用户卡到期
            Date date=TimeUtil.addDay(new Date(),renew_add_day);
            card.setAbled(true);
            card.setEndTime(date);
            Date date2=expressMonthCardRepository.save(card).getEndTime();
            return date.compareTo(date2)==0;
        }else if(this.getRemainsTime(user_id)>0){   //没到期
            Date date=TimeUtil.addDay(card.getEndTime(),renew_add_day);
            card.setAbled(true);
            card.setEndTime(date);
            Date date2=expressMonthCardRepository.save(card).getEndTime();
            return date.compareTo(date2)==0;
        }
        return false;
    }

}
