package com.example.booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;

    @NotBlank @NotNull
    private String roleName;

}
