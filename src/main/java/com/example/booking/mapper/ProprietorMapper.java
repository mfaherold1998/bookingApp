package com.example.booking.mapper;

import com.example.booking.dto.ProprietorDto;
import com.example.booking.common.BaseMapper;
import com.example.booking.entity.Proprietor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProprietorMapper extends BaseMapper<ProprietorDto, Proprietor> {
    ProprietorDto toDto(Proprietor entity);
    Proprietor toEntity(ProprietorDto dto);
}
