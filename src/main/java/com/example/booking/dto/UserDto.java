package com.example.booking.dto;

import com.example.booking.common.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDto extends BaseDto {

    @NotBlank
    private String email;

    private String password;

    private Boolean confirmedEmail;

    private Set<RoleDto> roles;
}