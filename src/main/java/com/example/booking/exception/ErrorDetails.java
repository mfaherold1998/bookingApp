package com.example.booking.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(CustomException customException) {
        this.timestamp = customException.getTimestamp();
        this.message = customException.getMessage();
        this.details = customException.getDetails();
    }
}
