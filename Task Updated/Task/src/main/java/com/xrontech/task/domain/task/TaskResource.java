package com.xrontech.task.domain.task;

import com.xrontech.task.domain.user.UserRole;
import com.xrontech.task.dto.ApplicationResponseDTO;
import com.xrontech.task.dto.RequestMetaDTO;
import com.xrontech.task.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskResource {
    private final TaskService taskService;
    private final RequestMetaDTO requestMetaDTO;

    // Admin API
    @PostMapping("/create")
    public ResponseEntity<ApplicationResponseDTO> createTask(@Valid @RequestBody TaskRegisterDTO taskRegisterDTO) {
        if (requestMetaDTO.getUserRole().equals(UserRole.ADMIN.toString())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskRegisterDTO));
        } else {
            throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "INVALID_USER_ROLE", "Invalid User Role");
        }
    }

    // User API
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTask() {
        if (requestMetaDTO.getUserRole().equals(UserRole.REGULAR_USER.toString())) {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.searchTask());
        } else {
            throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "INVALID_USER_ROLE", "Invalid User Role");
        }
    }

    // Admin API & User API
    @GetMapping("/search/{id}")
    public ResponseEntity<Task> searchTask(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.searchTask(id));
    }

    // User API
    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateTask(@PathVariable("id") Long id, @Valid @RequestBody TaskUpdateDTO taskUpdateDTO) {
        if (requestMetaDTO.getUserRole().equals(UserRole.REGULAR_USER.toString())) {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(id, taskUpdateDTO));
        } else {
            throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "INVALID_USER_ROLE", "Invalid User Role");
        }
    }

    // Admin API
    @PutMapping("/update/{task-id}/{user-id}")
    public ResponseEntity<ApplicationResponseDTO> updateTask(@PathVariable("task-id") Long taskId, @PathVariable("user-id") Long userId, @Valid @RequestBody TaskUpdateAdminDTO taskUpdateAdminDTO) {
        if (requestMetaDTO.getUserRole().equals(UserRole.ADMIN.toString())) {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(taskId, userId, taskUpdateAdminDTO));
        } else {
            throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "INVALID_USER_ROLE", "Invalid User Role");
        }
    }

    // Admin API
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApplicationResponseDTO> deleteTask(@PathVariable("id") Long id) {
        if (requestMetaDTO.getUserRole().equals(UserRole.ADMIN.toString())) {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.deleteTask(id));
        } else {
            throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "INVALID_USER_ROLE", "Invalid User Role");
        }
    }
}
