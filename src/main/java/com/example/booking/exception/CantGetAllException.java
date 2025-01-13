package com.example.booking.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

public class CantGetAllException extends CustomException {
    @Builder
    public CantGetAllException() {
        super("Not allowed to GET all data", HttpStatus.FORBIDDEN);
    }

}