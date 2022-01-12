package com.khoding.auth.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khoding.auth.domain.organization.Organization;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @NotBlank
    @Column(name = "username")
    private String username;
    @JsonIgnore
    @NotBlank
    @Column(name = "password")
    private String password;
    @Column(name = "date_signed_up")
    private LocalDateTime dateSignedUp;
    @Column(name = "last_modified")
    private LocalDateTime lastmodified;
    @Column(name = "verified")
    private Boolean isVerfied;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> userRoles = new HashSet<>();
    @ManyToOne
    private Organization organization;

    public User() {
    }

    public User(String username, String password, LocalDateTime dateSignedUp,
                LocalDateTime lastmodified, Boolean isVerfied, Organization organization) {
        this.username = username;
        this.password = password;
        this.dateSignedUp = dateSignedUp;
        this.lastmodified = lastmodified;
        this.isVerfied = isVerfied;
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getDateSignedUp() {
        return dateSignedUp;
    }

    public void setDateSignedUp(LocalDateTime dateSignedUp) {
        this.dateSignedUp = dateSignedUp;
    }

    public Set<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public Organization getOrganization() {
        return organization;
    }

    public LocalDateTime getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(LocalDateTime lastmodified) {
        this.lastmodified = lastmodified;
    }

    public Boolean getVerfied() {
        return isVerfied;
    }

    public void setVerfied(Boolean verfied) {
        isVerfied = verfied;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public static User of(String username, String password, LocalDateTime dateSignedUp,
                          LocalDateTime lastmodified, Boolean isVerfied, Organization organization) {
        return new User(username, password, dateSignedUp, lastmodified, isVerfied, organization);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dateSignedUp=" + dateSignedUp +
                ", lastmodified=" + lastmodified +
                ", isVerfied=" + isVerfied +
                ", userRoles=" + userRoles +
                ", organization=" + organization +
                '}';
    }
}
