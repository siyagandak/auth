package com.khoding.auth.service.user;

import javax.validation.constraints.NotBlank;

public class AdminLoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String pin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "AdminLoginRequest{" +
                "username='" + username + '\'' +
                '}';
    }
}
