package com.example.booking.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

public class CantDeleteException extends CustomException {
    @Builder
    public CantDeleteException() {
        super("Not allowed to DELETE", HttpStatus.FORBIDDEN);
    }
}