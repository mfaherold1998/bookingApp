package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleDto extends BaseDto {
    private String name;
}
