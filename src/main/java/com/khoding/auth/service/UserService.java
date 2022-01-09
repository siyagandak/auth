package com.khoding.auth.service;

import com.khoding.auth.domain.login.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User getUserByUsername(String username);

    Page<User> findAllByOrganization_Id(Long organizationId, Pageable pageablee);
}
