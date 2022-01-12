package com.khoding.auth.service.user;

import com.khoding.auth.domain.otp.Otp;
import com.khoding.auth.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User getUserByUsername(String username);

    Page<User> findAllByOrganization_Id(Long organizationId, Pageable pageablee);

    Page<User> adminViewUsers(String username);

    Page<User> getAllUsers(Pageable pageable);

    User saveUser(User user);
}
