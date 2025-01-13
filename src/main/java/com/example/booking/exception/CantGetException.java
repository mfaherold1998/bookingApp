package com.example.booking.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

public class CantGetException extends CustomException {
    @Builder
    public CantGetException() {
        super("Not allowed to GET", HttpStatus.FORBIDDEN);
    }

}
