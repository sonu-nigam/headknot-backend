package org.workflow.Shared.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.workflow.Shared.DTO.ErrorDTO;

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
