package com.khoding.auth.service.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserSignUpRequest {
    @Size(max = 12)
    @NotBlank
    private String mobileNumber;
    @Size(min = 4, max = 6)
    @NotBlank
    private String pin;
    private Set<String> role;
    @NotBlank
    private Long organizationId;

    public UserSignUpRequest() {
    }

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

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public String toString() {
        return "UserSignUpRequest{" +
                "mobileNumber='" + mobileNumber + '\'' +
                ", role=" + role +
                ", organizationId=" + organizationId +
                '}';
    }
}
