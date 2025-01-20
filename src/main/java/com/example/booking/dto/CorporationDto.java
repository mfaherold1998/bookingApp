package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import com.example.booking.entity.Proprietor;
import com.example.booking.entity.Subdivision;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class CorporationDto extends BaseDto {

    @NotNull
    private String name;

    @NotBlank
    private Set<Proprietor> proprietors = new HashSet<>();

    @NotBlank
    private Set<Subdivision> subdivisions = new HashSet<>();
}
