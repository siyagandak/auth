package com.khoding.auth.domain.organization;

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

    public Organization() {
    }

    public Organization(String name, String code, String address) {
        this.name = name;
        this.code = code;
        this.address = address;
    }

    public static Organization buid(String name, String code, String address){
        return new Organization(name, code, address);
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
}
