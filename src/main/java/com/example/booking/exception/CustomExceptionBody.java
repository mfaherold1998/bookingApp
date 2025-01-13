package com.example.booking.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomExceptionBody {
    private Date timestamp;
    private String message;

    public CustomExceptionBody(CustomException customException) {
        this.timestamp = customException.getTimestamp();
        this.message = customException.getMessage();
    }
}
