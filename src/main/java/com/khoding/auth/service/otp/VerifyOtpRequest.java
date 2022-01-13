package com.khoding.auth.service.otp;

import javax.validation.constraints.NotBlank;

public class VerifyOtpRequest {
    @NotBlank
    private String mobileNumber;

    @NotBlank
    private String otp;

    private VerifyOtpRequest(String mobileNumber, String otp) {
        this.mobileNumber = mobileNumber;
        this.otp = otp;
    }

    public static VerifyOtpRequest of(String mobileNumber, String otp){
        return new VerifyOtpRequest(mobileNumber, otp);
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "VerifyOtpRequest{" +
                "mobileNumber='" + mobileNumber + '\'' +
                '}';
    }
}
