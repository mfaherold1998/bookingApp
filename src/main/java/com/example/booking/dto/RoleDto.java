package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto extends BaseDto {
    private String name;
}
