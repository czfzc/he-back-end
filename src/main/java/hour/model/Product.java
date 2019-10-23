package hour.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;
import org.springframework.lang.Nullable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "shop_product")
public class Product {

    @Id
    @GenericGenerator(name="idGenerator",strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    String id;
    @JsonIgnore
    @NotNull
    String buildingId;
    @NotNull
    String name;
    @NotNull
    Double price;
    @NotNull
    boolean abled;
    @NotNull
    int rest;
    @NotNull
    String typeId;
    @NotNull
    String imgLink;
    @Nullable
    String addition;
    @Transient
    Date time;
    @JsonIgnore
    boolean deled;
    @NotNull
    Integer salesVomume;

    public Integer getSalesVomume() {
        return salesVomume;
    }

    public void setSalesVomume(Integer salesVomume) {
        this.salesVomume = salesVomume;
    }

    public boolean isDeled() {
        return deled;
    }

    public void setDeled(boolean deled) {
        this.deled = deled;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isAbled() {
        return abled;
    }

    public void setAbled(boolean abled) {
        this.abled = abled;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }
}
