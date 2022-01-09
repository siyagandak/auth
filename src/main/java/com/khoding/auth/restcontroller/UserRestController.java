package com.khoding.auth.restcontroller;

import com.khoding.auth.domain.login.User;
import com.khoding.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        UserDto userDto = UserDto.of(user.getId(), user.getDateSignedUp().toString(), user.getUserRoles(), user.getOrganization().getName());
        return ResponseEntity.ok(userDto);
    }
}
