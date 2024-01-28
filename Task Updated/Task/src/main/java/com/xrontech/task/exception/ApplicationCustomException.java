package com.xrontech.task.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationCustomException extends RuntimeException {
    private HttpStatus status;
    private String code;
    private String message;
}
