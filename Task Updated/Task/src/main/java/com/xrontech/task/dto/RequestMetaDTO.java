package com.xrontech.task.dto;

import com.xrontech.task.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestMetaDTO {
    private Long id;
    private String name;
    private String email;
    private String userRole;
}
