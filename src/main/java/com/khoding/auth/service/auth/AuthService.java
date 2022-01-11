package com.khoding.auth.service.auth;

import com.khoding.auth.domain.user.User;
import com.khoding.auth.response.JwtResponse;
import com.khoding.auth.service.user.UserLoginRequest;
import com.khoding.auth.service.user.UserSignUpRequest;

public interface AuthService {
    User signUpUser(UserSignUpRequest userSignUpRequest);

    Boolean checkUserExits(String username);

    JwtResponse siginUser(UserLoginRequest userLoginRequest);

    Integer generateOtp();
}
