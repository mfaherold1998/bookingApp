package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProcedureDto extends BaseDto {

    @NotNull
    private String name;

    @NotNull
    private Double price;

    private int timeInMinutes;
}
