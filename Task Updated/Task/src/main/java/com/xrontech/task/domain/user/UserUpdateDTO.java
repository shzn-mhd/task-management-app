package com.xrontech.task.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String password;
}
