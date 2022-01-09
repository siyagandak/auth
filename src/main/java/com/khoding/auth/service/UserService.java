package com.khoding.auth.service;

import com.khoding.auth.domain.login.User;

public interface UserService {
    User getUserByUsername(String username);
}
