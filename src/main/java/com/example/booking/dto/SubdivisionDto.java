package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class SubdivisionDto extends BaseDto {

    @NotNull
    private String name;

    private String adress;

    @Email
    private String email;

    @NotNull
    @Valid
    private CorporationDto corporation;
}
