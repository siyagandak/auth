package com.khoding.auth.service.otp;

import javax.validation.constraints.NotBlank;

public class OtpRequest {
    @NotBlank
    private String mobileNUmber;

    public OtpRequest() {
    }

    public OtpRequest(String mobileNUmber) {
        this.mobileNUmber = mobileNUmber;
    }

    public String getMobileNUmber() {
        return mobileNUmber;
    }

    public void setMobileNUmber(String mobileNUmber) {
        this.mobileNUmber = mobileNUmber;
    }

    public static OtpRequest of(String mobileNUmber) {
        return new OtpRequest(mobileNUmber);
    }

    @Override
    public String toString() {
        return "OtpRequest{" +
                "mobileNUmber='" + mobileNUmber + '\'' +
                '}';
    }
}
