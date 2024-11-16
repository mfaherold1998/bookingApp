package com.example.booking.Dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CorporationDto extends BaseDto {
    @NotNull
    @Size(min=1,max=100)
    private String name;
}
