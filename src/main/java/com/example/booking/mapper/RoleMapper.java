package com.example.booking.mapper;

import com.example.booking.common.BaseMapper;
import com.example.booking.dto.RoleDto;
import com.example.booking.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<RoleDto, RoleEntity> {

    RoleEntity toEntity(RoleDto dto);
    RoleDto toDto(RoleEntity entity);

}
