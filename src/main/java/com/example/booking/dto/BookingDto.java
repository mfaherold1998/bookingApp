package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BookingDto extends BaseDto {

    @NotNull
    @Valid
    private EmployeeDto employee;

    @NotNull
    @Valid
    private ProcedureDto procedure;

    @NotNull
    @Valid
    private ClientDto client;

    private LocalDate bookingDay;

    private LocalTime startTime;

    private LocalTime endTime;

    private String status;
}
