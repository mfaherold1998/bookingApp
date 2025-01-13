package com.example.booking.exception;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@EqualsAndHashCode(callSuper = true)
@Getter
public class AuthException extends CustomException {
    @Builder
    public AuthException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}