package hour.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class ExpressAdmin {
    @Id
    String expressId;
    int status;
    boolean abled;
    String expressPoint;
    String name;
    int payed;
    String phoneNum;
    String preorderId;
    String receiveCode;
    String sendMethod;
    String senderAdminId;
    String sizeName;
    String smsContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    Date time;
    Double totalFee;
    String userId;
    String address;
}
