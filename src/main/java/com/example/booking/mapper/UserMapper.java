package com.example.booking.mapper;

import com.example.booking.common.BaseMapper;
import com.example.booking.dto.UserDto;
import com.example.booking.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDto, UserEntity> {
    
    UserEntity toEntity(UserDto dto);
    UserDto toDto(UserEntity entity);

}
