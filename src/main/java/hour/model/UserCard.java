package hour.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="user_card")
public class UserCard {

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    String cardTypeId;
    String cardId;
    String userId;
    Date endDate;
    boolean abled;
    boolean newed;
    String extraData1;
    String extraData2;
    String extraData3;

    public String getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(String cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isAbled() {
        return abled;
    }

    public void setAbled(boolean abled) {
        this.abled = abled;
    }

    public boolean isNewed() {
        return newed;
    }

    public void setNewed(boolean newed) {
        this.newed = newed;
    }

    public String getExtraData1() {
        return extraData1;
    }

    public void setExtraData1(String extraData1) {
        this.extraData1 = extraData1;
    }

    public String getExtraData2() {
        return extraData2;
    }

    public void setExtraData2(String extraData2) {
        this.extraData2 = extraData2;
    }

    public String getExtraData3() {
        return extraData3;
    }

    public void setExtraData3(String extraData3) {
        this.extraData3 = extraData3;
    }
}
