package hour.model;

import javax.persistence.*;

@Entity
@Table(name="user_inf")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer mainkey;
    private String userId;
    private String openId;
    private String sessionId;
    private String mysession;
    private boolean abled;

    public boolean isAbled() {
        return abled;
    }

    public void setAbled(boolean abled) {
        this.abled = abled;
    }

    public String getMysession() {
        return mysession;
    }

    public void setMysession(String mysession) {
        this.mysession = mysession;
    }

    public Integer getMainkey() {
        return mainkey;
    }

    public void setMainkey(Integer mainkey) {
        this.mainkey = mainkey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


}
