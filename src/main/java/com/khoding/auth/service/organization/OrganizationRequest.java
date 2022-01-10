package com.khoding.auth.service.organization;

import javax.validation.constraints.NotBlank;

public class OrganizationRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String code;
    @NotBlank
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "OrganizatonRequest{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
