package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ProcedureDto extends BaseDto {

    @NotNull
    private String name;

    @NotNull
    private Double price;

    private int timeInMinutes;
}
