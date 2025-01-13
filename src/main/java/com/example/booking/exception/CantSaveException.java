package com.example.booking.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

public class CantSaveException extends CustomException {
    @Builder
    public CantSaveException() {
        super("Not allowed to SAVE", HttpStatus.FORBIDDEN);
    }
}
