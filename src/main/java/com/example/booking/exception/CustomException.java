package com.example.booking.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class CustomException extends RuntimeException{

    private final Date timestamp;
    private final String message;
    private final String details;
    private final HttpStatus httpStatus;

    public CustomException(String message, String details, HttpStatus httpStatus) {
        this.timestamp = new Date();
        this.message = message;
        this.details = details;
        this.httpStatus = httpStatus;
    }
}

