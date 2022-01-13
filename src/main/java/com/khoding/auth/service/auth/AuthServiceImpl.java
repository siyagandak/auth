package com.khoding.auth.service.auth;

import com.khoding.auth.domain.organization.Organization;
import com.khoding.auth.domain.user.Role;
import com.khoding.auth.domain.user.User;
import com.khoding.auth.domain.user.UserRole;
import com.khoding.auth.repository.organization.OrganizationRepository;
import com.khoding.auth.repository.user.RoleRepository;
import com.khoding.auth.repository.user.UserRepository;
import com.khoding.auth.response.JwtResponse;
import com.khoding.auth.security.JwtUtils;
import com.khoding.auth.service.user.UserLoginRequest;
import com.khoding.auth.service.user.UserSignUpRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final static String LOGGER_PREFIX = "[SignUp Service]";
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, OrganizationRepository organizationRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.organizationRepository = organizationRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public User signUpUser(UserSignUpRequest userSignUpRequest) {
        LOGGER.info("{} SigningUp User ={}", LOGGER_PREFIX, userSignUpRequest);
        Organization organization = organizationRepository.findById(userSignUpRequest.getOrganizationId()).get();
        User user = User.of(formatUsername(userSignUpRequest.getMobileNumber()), passwordEncoder.encode(userSignUpRequest.getPin()),
                LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, organization);
        Set<String> user_roles = userSignUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (Objects.isNull(user_roles)) {
            Role userRole = roleRepository.findByUserRole(UserRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(userRole);
        } else {
            user_roles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByUserRole(UserRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Role not found"));
                        roles.add(adminRole);
                        break;
                    case "eadmin":
                        Role eadminRole = roleRepository.findByUserRole(UserRole.ROLE_EADMIN)
                                .orElseThrow(() -> new RuntimeException("Role not found"));
                        roles.add(eadminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByUserRole(UserRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Role not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setUserRoles(roles);
        return userRepository.save(user);
    }

    private String formatUsername(String username) {
        if (StringUtils.startsWithAny(username, "00", "+")) {
            LOGGER.info("{} Format user name", LOGGER_PREFIX);
            username = username.startsWith("00") ? StringUtils.remove(username, "00") :
                    StringUtils.remove(username, "+");
        }
        if (StringUtils.containsAny(username, " ")) {
            username = StringUtils.remove(username, " ");
        }
        return username;
    }

    @Override
    public Boolean checkUserExits(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public JwtResponse siginUser(UserLoginRequest userLoginRequest) {
        LOGGER.info("{} signing in user {}", LOGGER_PREFIX, userLoginRequest);
        Authentication authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getMobileNumber(), userLoginRequest.getPin()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return JwtResponse.buildJwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles);
    }
}
