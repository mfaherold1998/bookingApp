package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ProprietorDto extends BaseDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Email
    private String email;
}
