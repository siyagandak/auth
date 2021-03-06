package com.khoding.auth.restcontroller.user;

import com.khoding.auth.domain.user.User;
import com.khoding.auth.response.MessageResponse;
import com.khoding.auth.service.user.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUser/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (Objects.isNull(user)) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.buildMessage("User record not found"));
        }
        UserDto userDto = UserDto.of(user.getId(), user.getDateSignedUp().toString(),
                user.getLastmodified().toString(), user.getUserRoles(), user.getOrganization().getName());
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/getAllUsers/{organizationId}")
    @PreAuthorize("hasAnyRole('EADMIN', 'ADMIN')")
    public ResponseEntity<?> getAllUsersByOrganizationId(@PathVariable Long organizationId) {
        return ResponseEntity.ok(userService.findAllByOrganization_Id(organizationId, Pageable.unpaged()));
    }

    @GetMapping("/admin-view")
    public ResponseEntity<?> adminView(Principal principal) {
        return ResponseEntity.ok(userService.adminViewUsers(principal.getName()));
    }

    @GetMapping("/all/users")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers(Pageable.unpaged()));
    }
}
