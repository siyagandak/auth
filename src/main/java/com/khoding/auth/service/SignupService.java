package com.khoding.auth.service;

import com.khoding.auth.domain.login.User;
import com.khoding.auth.response.JwtResponse;

public interface SignupService {
    User signUpUser(SignUpRequest signUpRequest);

    Boolean checkUserExits(String username);

    JwtResponse siginUser(LoginRequest loginRequest);
}
