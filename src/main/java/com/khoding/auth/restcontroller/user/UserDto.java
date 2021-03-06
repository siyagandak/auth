package com.khoding.auth.restcontroller.user;

import com.khoding.auth.domain.user.Role;

import java.util.Set;

public class UserDto {
    private Long id;
    private String user_organization;
    private Set<Role> user_role;
    private String dateSignedUp;
    private String dateLastModified;

    public UserDto(Long id, String dateSignedUp, String dateLastModified, Set<Role> user_role, String user_organization) {
        this.id = id;
        this.dateSignedUp = dateSignedUp;
        this.dateLastModified = dateLastModified;
        this.user_role = user_role;
        this.user_organization = user_organization;
    }

    public static UserDto of(Long id, String dateSignedUp, String dateLastModified, Set<Role> user_role, String user_organization){
        return  new UserDto(id, dateSignedUp, dateLastModified, user_role, user_organization);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateSignedUp() {
        return dateSignedUp;
    }

    public void setDateSignedUp(String dateSignedUp) {
        this.dateSignedUp = dateSignedUp;
    }

    public Set<Role> getUser_role() {
        return user_role;
    }

    public void setUser_role(Set<Role> user_role) {
        this.user_role = user_role;
    }

    public String getUser_organization() {
        return user_organization;
    }

    public void setUser_organization(String user_organization) {
        this.user_organization = user_organization;
    }

    public String getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(String dateLastModified) {
        this.dateLastModified = dateLastModified;
    }
}
