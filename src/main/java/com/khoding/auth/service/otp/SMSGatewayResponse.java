package com.khoding.auth.service.otp;

public class SMSGatewayResponse {
    private int status;
    private int credits;
    private String result;

    public SMSGatewayResponse() {
    }

    public SMSGatewayResponse(int status, int credits, String result) {
        this.status = status;
        this.credits = credits;
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SMSGatewayResponse{" +
                "status=" + status +
                ", credits=" + credits +
                ", result='" + result + '\'' +
                '}';
    }
}
