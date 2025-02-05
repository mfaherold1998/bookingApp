package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CorporationDto extends BaseDto {
    @NotNull
    private String name;
}
