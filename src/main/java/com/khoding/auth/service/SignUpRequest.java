package com.khoding.auth.service;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class SignUpRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String pin;
    private Set<String> role;

    public SignUpRequest() {
    }

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

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "SignUpRequest{" +
                "username='" + username + '\'' +
                '}';
    }
}
