package com.example.headknot.Shared.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorDTO {
    public ErrorDTO(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

    Integer code;
    String status;
    String message;
}
