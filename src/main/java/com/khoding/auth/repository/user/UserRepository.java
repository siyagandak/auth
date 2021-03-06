package com.khoding.auth.repository.user;

import com.khoding.auth.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Boolean existsByUsername(String username);

    Page<User> findAllByOrganization_Id(Long organizationId,  Pageable pageablee);
}
