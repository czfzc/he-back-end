package hour.model;

import javax.persistence.*;

@Entity
@Table(name="user_express_price")
public class ExpressPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer mainkey;
    String destBuildingId;
    String expressPointId;
    Double price;
    String sizeId;
    String sendMethodId;

    public String getSendMethodId() {
        return sendMethodId;
    }

    public void setSendMethodId(String sendMethodId) {
        this.sendMethodId = sendMethodId;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public Integer getMainkey() {
        return mainkey;
    }

    public void setMainkey(Integer mainkey) {
        this.mainkey = mainkey;
    }

    public String getDestBuildingId() {
        return destBuildingId;
    }

    public void setDestBuildingId(String destBuildingId) {
        this.destBuildingId = destBuildingId;
    }

    public String getExpressPointId() {
        return expressPointId;
    }

    public void setExpressPointId(String expressPointId) {
        this.expressPointId = expressPointId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
