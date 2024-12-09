package com.example.booking.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends CustomException{

    @Builder
    public AuthException(String message, String details){super(message,details, HttpStatus.FORBIDDEN);}
}