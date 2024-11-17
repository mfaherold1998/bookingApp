package com.example.booking.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(CustomException ex) {
        log.error(ex.getTimestamp().toString(), ex.getMessage(), ex.getDetails());
        return new ResponseEntity<>(new ErrorDetails(ex),ex.getHttpStatus());
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex) {
        log.error(ex.toString());
        CustomException exception = new CustomException(ex.getMessage(),"",HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new ErrorDetails(exception),exception.getHttpStatus());
    }
}