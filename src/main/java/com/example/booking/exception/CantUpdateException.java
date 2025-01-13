package com.example.booking.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

public class CantUpdateException extends CustomException {
    @Builder
    public CantUpdateException() {
        super("Not allowed to PUT", HttpStatus.FORBIDDEN);
    }
}
