package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EmployeeDto extends BaseDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Email
    private String email;

    //@Valid
    private EmployeeAgendaDto agenda;

    //@NotNull
    //@Valid
    private SubdivisionDto subdivision;
}
