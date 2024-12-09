package com.example.booking.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConstraintException extends CustomException {

    @Builder
    public ConstraintException(String message, String details){
        super(message,details, HttpStatus.BAD_REQUEST);
    }
}
