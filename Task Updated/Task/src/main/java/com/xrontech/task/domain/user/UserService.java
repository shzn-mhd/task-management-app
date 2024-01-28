package com.xrontech.task.domain.user;

import com.xrontech.task.dto.ApplicationResponseDTO;
import com.xrontech.task.dto.RequestMetaDTO;
import com.xrontech.task.exception.ApplicationCustomException;
import com.xrontech.task.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RequestMetaDTO requestMetaDTO;
    private final JwtUtils jwtUtils;

    public ApplicationResponseDTO createUser(UserRegisterDTO userRegisterDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(userRegisterDTO.getEmail());
        if (optionalUser.isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "THIS_EMAIL_ALREADY_EXIST", "This Email Already Exist");
        } else {
            userRepository.save(new User.UserBuilder().name(userRegisterDTO.getName()).email(userRegisterDTO.getEmail()).password(userRegisterDTO.getPassword()).userRole(UserRole.REGULAR_USER).build());
            return new ApplicationResponseDTO(HttpStatus.CREATED, "USER_CREATED_SUCCESSFULLY", "User Created Successfully!");
        }
    }

    public Map<String, Object> logIn(UserLogInDTO userLogInDTO) {
        User user = userRepository.findByEmail(userLogInDTO.getEmail()).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "EMAIL_NOT_FOUND", "Email not Found"));
        if (user.getPassword().equals(userLogInDTO.getPassword())) {
            String accessToken = jwtUtils.generateAccessToken(user);
            Map<String, Object> logInResponse = new HashMap<>();
            logInResponse.put("accessToken", accessToken);
            logInResponse.put("status", HttpStatus.OK);
            logInResponse.put("code", "USER_LOGGED_IN_SUCCESSFULLY");
            logInResponse.put("message", "User Logged In Successfully!");
            return logInResponse;
        } else {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_PASSWORD", "Invalid Password");
        }
    }

    public ApplicationResponseDTO updateUser(UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(requestMetaDTO.getId()).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_USER_ID", "Invalid User Id"));
        user.setName(userUpdateDTO.getName());
        user.setPassword(userUpdateDTO.getPassword());
        userRepository.save(user);
        return new ApplicationResponseDTO(HttpStatus.OK, "USER_UPDATED_SUCCESSFULLY", "User Updated Successfully!");
    }

    public ApplicationResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_USER_ID", "Invalid User Id"));
        user.setName(userUpdateDTO.getName());
        user.setPassword(userUpdateDTO.getPassword());
        userRepository.save(user);
        return new ApplicationResponseDTO(HttpStatus.OK, "USER_UPDATED_SUCCESSFULLY", "User Updated Successfully!");
    }
}
