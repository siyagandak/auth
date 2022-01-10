package com.khoding.auth.service.user;

import javax.validation.constraints.NotBlank;

public class UserLoginRequest {
    @NotBlank
    private String mobileNumber;
    @NotBlank
    private String pin;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "mobileNumber='" + mobileNumber + '\'' +
                '}';
    }
}
