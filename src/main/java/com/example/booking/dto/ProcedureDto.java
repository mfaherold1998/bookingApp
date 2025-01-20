package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcedureDto extends BaseDto {

    @NotNull
    private String name;

    @NotNull
    private Double price;

    private int timeInMinutes;
}
