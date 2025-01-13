package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleDto extends BaseDto {
    private String name;
}
