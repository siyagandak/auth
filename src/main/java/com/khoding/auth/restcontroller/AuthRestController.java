package com.khoding.auth.restcontroller;

import com.khoding.auth.domain.login.User;
import com.khoding.auth.repository.UserRepository;
import com.khoding.auth.response.JwtResponse;
import com.khoding.auth.response.MessageResponse;
import com.khoding.auth.security.JwtUtils;
import com.khoding.auth.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final SignupService signupService;
    private final static String LOGGER_PREFIX = "[Auth Controller]";
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthRestController.class);

    public AuthRestController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping("/signup/user")
    public ResponseEntity<?> signUpUser(@RequestBody @Valid UserSignUpRequest userSignUpRequest) {
        LOGGER.info("{} SignUp request... {}", LOGGER_PREFIX, userSignUpRequest);
        if (signupService.checkUserExits(userSignUpRequest.getMobileNumber())) {
            LOGGER.info("{} Username {} exits => {}", LOGGER_PREFIX, userSignUpRequest.getMobileNumber(),
                    signupService.signUpUser(userSignUpRequest));
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.buildMessage("Error: Username already exists"));
        }
        User user = signupService.signUpUser(userSignUpRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<?> signUpAdmin(@RequestBody @Valid AdminSignUpRequest adminSignUpRequest) {
        return null;
    }

    @PostMapping("signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid LoginRequest loginRequest) {
        LOGGER.info("{} SignIn request.... {}", LOGGER_PREFIX, loginRequest);
        JwtResponse jwtResponse = signupService.siginUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}
