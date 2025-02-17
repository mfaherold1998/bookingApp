package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RoleDto extends BaseDto {

    private String name;
}
