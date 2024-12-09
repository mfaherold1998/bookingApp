package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto extends BaseDto {
    @NotBlank
    private String firstName;
    private String lastName;
    private String email;
    private Set<RoleDto> roles;
}