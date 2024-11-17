package com.example.booking.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException{

    public NotFoundException (String message, String details){super(message,details, HttpStatus.NOT_FOUND);}
}