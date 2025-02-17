package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProprietorDto extends BaseDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

}
