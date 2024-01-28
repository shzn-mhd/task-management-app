package com.xrontech.task.domain.user;

import com.xrontech.task.dto.ApplicationResponseDTO;
import com.xrontech.task.dto.RequestMetaDTO;
import com.xrontech.task.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserResource {
    private final UserService userService;
    private final RequestMetaDTO requestMetaDTO;

    // User Public API
    @PostMapping("/register")
    public ResponseEntity<ApplicationResponseDTO> createUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(userService.createUser(userRegisterDTO));
    }

    // Public API
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> logIn(@RequestBody UserLogInDTO userLogInDTO) {
        return ResponseEntity.ok(userService.logIn(userLogInDTO));
    }

    // User API
    @PutMapping("/update")
    public ResponseEntity<ApplicationResponseDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        if (requestMetaDTO.getUserRole().equals(UserRole.REGULAR_USER.toString())) {
            return ResponseEntity.ok(userService.updateUser(userUpdateDTO));
        } else {
            throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "INVALID_USER_ROLE", "Invalid User Role");
        }
    }

    // Admin API
    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        if (requestMetaDTO.getUserRole().equals(UserRole.ADMIN.toString())) {
            return ResponseEntity.ok(userService.updateUser(id, userUpdateDTO));
        } else {
            throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "INVALID_USER_ROLE", "Invalid User Role");
        }
    }
}