package hour.service;

import hour.model.*;
import hour.repository.CardGroupRepository;
import hour.repository.CardRepository;
import hour.repository.CardTypeRepository;
import hour.repository.MoreProductRepository;
import hour.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

@Service("CardService")
public class CardServiceImpl implements CardService{

    @Autowired
    CardGroupRepository cardGroupRepository;

    @Autowired
    CardTypeRepository cardTypeRepository;

    @Autowired
    MoreProductRepository moreProductRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    CardRepository cardRepository;

    @Override
    public List<CardGroup> getCards(String user_id){
        return cardGroupRepository.findAll(user_id);
    }

    @Override
    public String buyCard(String user_id, String card_type_id){
        CardType cardType = cardTypeRepository.findById(card_type_id).get();
        MoreProduct moreProduct = moreProductRepository.findFirstByProductId(cardType.getMoreProductId());
        return orderService.cardPayOrder(user_id,card_type_id);
    }

    @Value("${express.card.renew-add-times}")
    int renew_add_times;

    @Value("${express.card.renew-add-day}")
    int renew_add_day;

    @Override
    public boolean reNew(String user_id, String card_type_id){
        if("123".equals(card_type_id)){
            UserCard userCard = cardRepository.findFirstByUserIdAndCardTypeId(user_id,card_type_id);
            if(userCard==null){
                Date date= TimeUtil.addDay(new Date(),renew_add_day);
                userCard = new UserCard();
                userCard.setAbled(true);
                userCard.setCardTypeId("123");
                userCard.setEndDate(date);
                userCard.setNewed(true);
                userCard.setUserId(user_id);
                userCard.setExtraData1(String.valueOf(renew_add_times)); //extra_data_1代表剩余次数
            }else if(getRemainsTime(user_id,userCard.getCardId())==0){ //到期或次数用完
                Date date= TimeUtil.addDay(new Date(),renew_add_day);
                userCard.setAbled(true);
                userCard.setEndDate(date);
                userCard.setNewed(false);
                userCard.setExtraData1(String.valueOf(renew_add_times));
            }else if(getRemainsTime(user_id,userCard.getCardId())>0){   //次数未用完或者没有到期
                Date date= TimeUtil.addDay(userCard.getEndDate(),renew_add_day);
                userCard.setAbled(true);
                userCard.setEndDate(date);
                userCard.setNewed(false);
                userCard.setExtraData1(String.valueOf(renew_add_times));
                userCard.setExtraData1(String.valueOf(Integer.parseInt(userCard.getExtraData1())+renew_add_times));
            }
            return cardRepository.save(userCard).getCardId()!=null;
        }
        return false;
    }

    @Override
    public int getRemainsTime(String user_id,String card_id) {
        int sum=0;
        UserCard card = cardRepository.findFirstByUserIdAndCardIdAndAbledTrue(user_id,card_id);
        if (card == null) return 0;
        int last=(int)TimeUtil.getTimeDiffDate(card.getEndDate(),new Date());
        return last<0||Integer.parseInt(card.getExtraData1())<=0?0:last;
    }

}
