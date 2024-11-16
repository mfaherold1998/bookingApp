package com.example.booking.Dto;

import com.example.booking.common.BaseDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAgendaDto extends BaseDto {
    private LocalDate day;
    private LocalTime start;
    private LocalTime end;
}
