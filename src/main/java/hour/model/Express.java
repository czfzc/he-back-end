package hour.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_express")
public class Express {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    Integer mainkey;
    String expressId;
    @JsonIgnore
    String preorderId;
    @JsonIgnore
    String addressId;
    String name;
    String phoneNum;
    Double totalFee;
    String smsContent;
    String receiveCode;
    String userId;
    String sizeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    Date time;
    int Status;
    boolean abled;
    String expressPointId;
    String sendMethodId;
    @JsonIgnore
    String senderAdminId;
    @Transient
    Admin sender;

    public Admin getSender() {
        return sender;
    }

    public void setSender(Admin sender) {
        this.sender = sender;
    }

    public String getSenderAdminId() {
        return senderAdminId;
    }

    public void setSenderAdminId(String senderAdminId) {
        this.senderAdminId = senderAdminId;
    }

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

    public String getExpressPointId() {
        return expressPointId;
    }

    public void setExpressPointId(String expressPointId) {
        this.expressPointId = expressPointId;
    }

    public boolean isAbled() {
        return abled;
    }

    public void setAbled(boolean abled) {
        this.abled = abled;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getMainkey() {
        return mainkey;
    }

    public void setMainkey(Integer mainkey) {
        this.mainkey = mainkey;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getPreorderId() {
        return preorderId;
    }

    public void setPreorderId(String preorderId) {
        this.preorderId = preorderId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
    }
}
