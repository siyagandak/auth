package com.khoding.auth.service.otp;

public class MessageRequest {
    String message;
    String msisdn;

    public MessageRequest(String message, String msisdn) {
        this.message = message;
        this.msisdn = msisdn;
    }

    public static MessageRequest of(String message, String msisdn) {
        return new MessageRequest(message, msisdn);
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
}
