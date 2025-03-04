package com.example.booking.exception;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Getter
public class NotFoundException extends CustomException {
    @Builder
    public NotFoundException() {
        super("Not Found Exception", HttpStatus.NOT_FOUND);
    }
}
