package hour.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;

@Entity
@Table(name="user_service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer Id;
    @JsonIgnore
    Integer mainkey;
    @JsonIgnore
    String modelName;
    String name;
    @JsonIgnore
    String protrol;
    boolean abled;
    boolean show;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtrol() {
        return protrol;
    }

    public void setProtrol(String protrol) {
        this.protrol = protrol;
    }

    public boolean isAbled() {
        return abled;
    }

    public void setAbled(boolean abled) {
        this.abled = abled;
    }

    public Integer getMainkey() {
        return mainkey;
    }

    public void setMainkey(Integer mainkey) {
        this.mainkey = mainkey;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }


    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
