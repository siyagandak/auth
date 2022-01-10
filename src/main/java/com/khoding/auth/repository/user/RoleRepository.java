package com.khoding.auth.repository.user;

import com.khoding.auth.domain.user.Role;
import com.khoding.auth.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByUserRole(UserRole userRole);
}
