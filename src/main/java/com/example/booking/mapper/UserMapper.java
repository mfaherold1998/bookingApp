package com.example.booking.mapper;

import com.example.booking.common.BaseMapper;
import com.example.booking.dto.UserDto;
import com.example.booking.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDto, User> {
    UserDto toDto(User entity);
    User toEntity(UserDto dto);
}

