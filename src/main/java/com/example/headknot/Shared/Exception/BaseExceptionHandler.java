package com.example.headknot.Shared.Exception;

import com.example.headknot.Shared.DTO.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleTaskNotFoundException(NotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorDTO errorDTO = new ErrorDTO(status, exception.getMessage());
        return ResponseEntity.status(status).body(errorDTO);
    }
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorDTO errorDTO = new ErrorDTO(status, exception.getMessage());
        return ResponseEntity.status(status).body(errorDTO);
    }
}
