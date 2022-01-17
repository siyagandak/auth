package com.khoding.auth.domain.organization;

import com.khoding.auth.domain.utils.Status;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 35)
    @Column(name = "name")
    private String name;
    @Size(min = 3, max = 10)
    @Column(name = "code")
    private String code;
    @Size(max = 50)
    @Column(name = "address")
    private String address;
    @Column(name = "status")
    private Status status;

    public Organization() {
    }

    public Organization(String name, String code, String address, Status status) {
        this.name = name;
        this.code = code;
        this.address = address;
        this.status = status;
    }

    public static Organization build(String name, String code, String address, Status status){
        return new Organization(name, code, address, status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Organization{" +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                '}';
    }
}
