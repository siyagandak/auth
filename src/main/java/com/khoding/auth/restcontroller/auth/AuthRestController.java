package com.khoding.auth.restcontroller.auth;

import com.khoding.auth.domain.user.User;
import com.khoding.auth.response.JwtResponse;
import com.khoding.auth.response.MessageResponse;
import com.khoding.auth.service.user.AdminSignUpRequest;
import com.khoding.auth.service.user.UserLoginRequest;
import com.khoding.auth.service.auth.AuthService;
import com.khoding.auth.service.user.UserSignUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final AuthService authService;
    private static final String LOGGER_PREFIX = "[Auth Controller]";
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRestController.class);

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup/user")
    public ResponseEntity<?> signUpUser(@RequestBody @Valid UserSignUpRequest userSignUpRequest) {
        LOGGER.info("{} SignUp request... {}", LOGGER_PREFIX, userSignUpRequest);
        if (authService.checkUserExits(userSignUpRequest.getMobileNumber())) {
            LOGGER.info("{} Username {} exits => {}", LOGGER_PREFIX, userSignUpRequest.getMobileNumber(),
                    authService.signUpUser(userSignUpRequest));
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.buildMessage("Error: Username already exists"));
        }
        User user = authService.signUpUser(userSignUpRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<?> signUpAdmin(@RequestBody @Valid AdminSignUpRequest adminSignUpRequest) {
        return null;
    }

    @PostMapping("signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        LOGGER.info("{} SignIn request.... {}", LOGGER_PREFIX, userLoginRequest);
        JwtResponse jwtResponse = authService.siginUser(userLoginRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}
