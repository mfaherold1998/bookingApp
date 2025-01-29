package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends BaseDto {

    @NotBlank
    private String email;

    private String password;

    private Boolean confirmedEmail;

    private Set<RoleDto> roles;
}