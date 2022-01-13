package com.khoding.auth.service.otp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OtpSMS {
    @Value("${app.smsUser}")
    private String user;
    @JsonIgnore
    @Value("${app.smsPassword}")
    private String password;
    private String message;
    private String msisdn;

    public OtpSMS() {
    }

    public OtpSMS(String user, String password, String message, String msisdn) {
        this.user = user;
        this.password = password;
        this.message = message;
        this.msisdn = msisdn;
    }

    public OtpSMS(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public static OtpSMS of(String user, String password, String message, String msisdn) {
        return new OtpSMS(user, password, message, msisdn);
    }

    public OtpSMS deliverOtp(MessageRequest messageRequest) {
        return new OtpSMS(user, password, messageRequest.getMessage(), messageRequest.getMsisdn());
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return "OtpSMS{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", message='" + message + '\'' +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
