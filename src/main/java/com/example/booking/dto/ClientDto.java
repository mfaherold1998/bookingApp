package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ClientDto extends BaseDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Email
    private String email;
}
