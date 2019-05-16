package hour.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user_voucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer mainkey;
    String cardId;
    String typeId;
    String name;
    boolean abled;
    boolean used;
    Date checkTime;
    String checkUserId;

    public Integer getMainkey() {
        return mainkey;
    }

    public void setMainkey(Integer mainkey) {
        this.mainkey = mainkey;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAbled() {
        return abled;
    }

    public void setAbled(boolean abled) {
        this.abled = abled;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }
}
