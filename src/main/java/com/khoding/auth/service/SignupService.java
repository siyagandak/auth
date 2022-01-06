package com.khoding.auth.service;

import com.khoding.auth.domain.login.User;

public interface SignupService {
    User signUpUser(SignUpRequest signUpRequest);

    Boolean checkUserExits(String username);
}
