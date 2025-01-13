package com.example.booking.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{

    private final Date timestamp;
    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        this.timestamp = new Date();
        this.message = message;
        this.httpStatus = httpStatus;
    }
}

