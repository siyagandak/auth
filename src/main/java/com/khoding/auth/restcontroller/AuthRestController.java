package com.khoding.auth.restcontroller;

import com.khoding.auth.domain.login.Role;
import com.khoding.auth.domain.login.User;
import com.khoding.auth.domain.login.UserRole;
import com.khoding.auth.repository.RoleRepository;
import com.khoding.auth.repository.UserRepository;
import com.khoding.auth.response.JwtResponse;
import com.khoding.auth.response.MessageResponse;
import com.khoding.auth.security.JwtUtils;
import com.khoding.auth.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final SignupService signupService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    private final static String LOGGER_PREFIX = "[Auth Controller]";
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthRestController.class);

    public AuthRestController(SignupService signupService, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils,
                              AuthenticationManager authenticationManager) {
        this.signupService = signupService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody @Valid SignUpRequest signUpRequest) {
        LOGGER.info("{} SignUp request... {}", LOGGER_PREFIX, signUpRequest);
        if (signupService.checkUserExits(signUpRequest.getUsername())) {
            LOGGER.info("{} Username {} exits => {}", LOGGER_PREFIX, signUpRequest.getUsername(),
                    signupService.signUpUser(signUpRequest));
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.buildMessage("Error: Username already exists"));
        }
        User user = signupService.signUpUser(signUpRequest);
//        return ResponseEntity.ok(MessageResponse.buildMessage("Success: User created successfully"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid LoginRequest loginRequest){
        LOGGER.info("{} SignIn request.... {}", LOGGER_PREFIX, loginRequest);
        Authentication authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPin()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
    }

}
