package com.khoding.auth.domain.login;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "username"))
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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> userRoles = new HashSet<>();

    public User() {
    }

    public User(String username, String password, LocalDateTime dateSignedUp) {
        this.username = username;
        this.password = password;
        this.dateSignedUp = dateSignedUp;
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

    public static User of(String username, String password, LocalDateTime dateSignedUp) {
        return new User(username, password, dateSignedUp);
    }
}
