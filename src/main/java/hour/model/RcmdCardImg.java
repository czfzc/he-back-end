package hour.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "rcmd_card_img")
public class RcmdCardImg {
    @Id
    @GenericGenerator(name="idGenerator",strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;
    private String img;
    private String uri;
    private String size;
    @JsonIgnore
    private boolean abled;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional=false)
    @JoinColumn(name="card_id")
    @JsonIgnore
    private RcmdCard card;

    public RcmdCard getCard() {
        return card;
    }

    public void setCard(RcmdCard card) {
        this.card = card;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isAbled() {
        return abled;
    }

    public void setAbled(boolean abled) {
        this.abled = abled;
    }
}
