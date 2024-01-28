package com.xrontech.task.domain.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TaskUpdateAdminDTO {
    @NotBlank
    private String taskName;
    @NotBlank
    private String description;
    @NotNull
    private Status status;
}
