package com.example.booking.mapper;

import com.example.booking.common.BaseMapper;
import com.example.booking.dto.RoleDto;
import com.example.booking.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<RoleDto, Role> {
    RoleDto toDto(Role entity);
    Role toEntity(RoleDto dto);
}

