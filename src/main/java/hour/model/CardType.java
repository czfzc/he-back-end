package hour.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_card_type")
public class CardType {
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    String cardTypeId;
    String cardTitle;
    String cardAddition;
    String img;
    String color;
    String moreProductId;
    boolean showed;
    boolean abled;
    String moreData1;
    String moreData2;
    String moreData3;

    public String getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(String cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getCardAddition() {
        return cardAddition;
    }

    public void setCardAddition(String cardAddition) {
        this.cardAddition = cardAddition;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMoreProductId() {
        return moreProductId;
    }

    public void setMoreProductId(String moreProductId) {
        this.moreProductId = moreProductId;
    }

    public boolean isShowed() {
        return showed;
    }

    public void setShowed(boolean showed) {
        this.showed = showed;
    }

    public boolean isAbled() {
        return abled;
    }

    public void setAbled(boolean abled) {
        this.abled = abled;
    }

    public String getMoreData1() {
        return moreData1;
    }

    public void setMoreData1(String moreData1) {
        this.moreData1 = moreData1;
    }

    public String getMoreData2() {
        return moreData2;
    }

    public void setMoreData2(String moreData2) {
        this.moreData2 = moreData2;
    }

    public String getMoreData3() {
        return moreData3;
    }

    public void setMoreData3(String moreData3) {
        this.moreData3 = moreData3;
    }
}
