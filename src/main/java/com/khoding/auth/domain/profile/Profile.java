package com.khoding.auth.domain.profile;

public class Profile {
    private String username;
    private Integer pin;

    public Profile() {
    }

    public Profile(String username, Integer pin) {
        this.username = username;
        this.pin = pin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "username='" + username + '\'' +
                ", pin=" + pin +
                '}';
    }
}
