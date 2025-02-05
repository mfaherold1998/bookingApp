package com.example.booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;

}

