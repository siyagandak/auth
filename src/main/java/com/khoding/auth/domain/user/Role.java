package com.khoding.auth.domain.user;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_role", length = 18)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public Role() {
    }

    public static Role of(UserRole userRole){
        Role role = new Role();
        role.setUserRole(userRole);
        return role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
