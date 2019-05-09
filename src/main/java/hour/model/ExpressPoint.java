package hour.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="user_express_point")
public class ExpressPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Integer mainkey;
    private String expressPointId;
    @JsonIgnore
    private String position;
    private String name;
    @JsonIgnore
    private String smsTemp;
    @JsonIgnore
    private String codeFormat;

    public Integer getMainkey() {
        return mainkey;
    }

    public void setMainkey(Integer mainkey) {
        this.mainkey = mainkey;
    }

    public String getExpressPointId() {
        return expressPointId;
    }

    public void setExpressPointId(String expressPointId) {
        this.expressPointId = expressPointId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmsTemp() {
        return smsTemp;
    }

    public void setSmsTemp(String smsTemp) {
        this.smsTemp = smsTemp;
    }

    public String getCodeFormat() {
        return codeFormat;
    }

    public void setCodeFormat(String codeFormat) {
        this.codeFormat = codeFormat;
    }
}
