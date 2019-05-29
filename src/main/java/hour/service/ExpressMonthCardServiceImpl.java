package hour.service;

import hour.model.ExpressMonthCard;
import hour.repository.ExpressMonthCardRepository;
import hour.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;

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
        return last<0||card.getLastTimes()<=0?0:last;
    }

    @Value("${express.card.renew-add-day}")
    int renew_add_day;

    @Value("${express.card.product-id}")
    String expressCardProductId;

    @Value("${express.card.renew-add-times}")
    int renew_add_times;
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
            card.setProductId(expressCardProductId);
            card.setAbled(true);
            card.setEndTime(date);
            card.setLastTimes(renew_add_times);
            Date date2=expressMonthCardRepository.save(card).getEndTime();
            return date.compareTo(date2)==0;
        }else if(this.getRemainsTime(user_id)==0){   //用户卡到期
            Date date=TimeUtil.addDay(new Date(),renew_add_day);
            card.setAbled(true);
            card.setEndTime(date);
            card.setLastTimes(renew_add_times);
            Date date2=expressMonthCardRepository.save(card).getEndTime();
            return date.compareTo(date2)==0;
        }else if(this.getRemainsTime(user_id)>0){   //没到期
            Date date=TimeUtil.addDay(card.getEndTime(),renew_add_day);
            card.setAbled(true);
            card.setEndTime(date);
            card.setLastTimes(card.getLastTimes()+renew_add_times);
            Date date2=expressMonthCardRepository.save(card).getEndTime();
            return date.compareTo(date2)==0;
        }
        return false;
    }

    /**
     * 消耗会员卡次数
     * @param use_times
     * @return
     */
    @Override
    public boolean useItForTimes(String user_id,int use_times){
        if(!this.isAbled(user_id)) return false;
        ExpressMonthCard card = expressMonthCardRepository.
                findFirstByUserIdAndAbledTrue(user_id);
        int new_last_times=card.getLastTimes()-use_times;
        if(new_last_times<0) return false;
        card.setLastTimes(new_last_times);
        return expressMonthCardRepository.save(card).getLastTimes()==new_last_times;
    }

    /**
     * 判断用户首次购买月卡
     */
    @Override
    public boolean isNoCard(String user_id){
        ExpressMonthCard card = expressMonthCardRepository.
                findFirstByUserId(user_id);
        return card==null;
    }
}
