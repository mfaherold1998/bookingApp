package com.example.booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank
    private String firstName;//quitar para dejar la info en client o proprietor
    @NotBlank
    private String lastName;//quitar para dejar la info en client o proprietor
    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;
}
