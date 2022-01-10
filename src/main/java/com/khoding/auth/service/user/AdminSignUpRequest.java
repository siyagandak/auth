package com.khoding.auth.service.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class AdminSignUpRequest {
    @Email
    @NotBlank
    private String username;
    @Size(max = 40)
    @NotBlank
    private String password;
    private Set<String> role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AdminSignUpRequest{" +
                "username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
