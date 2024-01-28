package com.xrontech.task.domain.task;

import com.xrontech.task.domain.user.User;
import com.xrontech.task.domain.user.UserRepository;
import com.xrontech.task.dto.ApplicationResponseDTO;
import com.xrontech.task.dto.RequestMetaDTO;
import com.xrontech.task.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final RequestMetaDTO requestMetaDTO;

    public ApplicationResponseDTO createTask(TaskRegisterDTO taskRegisterDTO) {
        User user = userRepository.findById(taskRegisterDTO.getUserId()).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "USER_ID_NOT_FOUND", "User Id Not Found"));
        taskRepository.save(Task.builder().taskName(taskRegisterDTO.getTaskName()).description(taskRegisterDTO.getDescription()).status(taskRegisterDTO.getStatus()).userId(user.getId()).build());
        return new ApplicationResponseDTO(HttpStatus.CREATED, "TASK_CREATED_SUCCESSFULLY", "Task Created Successfully!");
    }

    public List<Task> searchTask() {
        System.out.println(requestMetaDTO.getId());
        return taskRepository.findAllByUserId(requestMetaDTO.getId()).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "TASKS_NOT_FOUND", "Tasks Noy Found"));
    }

    public Task searchTask(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_TASK_ID", "Invalid Task Id"));
    }

    public ApplicationResponseDTO updateTask(Long id, TaskUpdateDTO taskUpdateDTO) {
        Task task = taskRepository.findByIdAndUserId(id, requestMetaDTO.getId()).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_TASK", "Invalid Task"));

        task.setStatus(taskUpdateDTO.getStatus());

        taskRepository.save(task);

        return new ApplicationResponseDTO(HttpStatus.OK, "TASK_UPDATED_SUCCESSFULLY", "Task Updated Successfully!");
    }

    public ApplicationResponseDTO updateTask(Long taskId, Long userId, TaskUpdateAdminDTO taskUpdateAdminDTO) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_TASK", "Invalid Task"));

        task.setTaskName(taskUpdateAdminDTO.getTaskName());
        task.setDescription(taskUpdateAdminDTO.getDescription());
        task.setStatus(taskUpdateAdminDTO.getStatus());
        taskRepository.save(task);

        return new ApplicationResponseDTO(HttpStatus.OK, "TASK_UPDATED_SUCCESSFULLY", "Task Updated Successfully!");
    }

    public ApplicationResponseDTO deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "TASK_NOT_FOUND", "Task not Found"));
        taskRepository.delete(task);
        return new ApplicationResponseDTO(HttpStatus.OK, "TASK_DELETED_SUCCESSFULLY", "Task Deleted Successfully!");
    }
}
