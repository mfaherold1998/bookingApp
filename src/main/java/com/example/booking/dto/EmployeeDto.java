package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto extends BaseDto {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    @Size(min=1,max=100)
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    @Size(min=1,max=100)
    private String lastName;

    @Email
    private String email;

    @Valid
    private EmployeeAgendaDto agenda;

    @NotNull
    @Valid
    private SubdivisionDto subdivision;
}
