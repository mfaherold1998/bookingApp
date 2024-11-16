package com.example.booking.Mapper;

import com.example.booking.dto.SubdivisionDto;
import com.example.booking.common.BaseMapper;
import com.example.booking.entity.Subdivision;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubdivisionMapper extends BaseMapper<SubdivisionDto, Subdivision> {
    SubdivisionDto toDto(Subdivision entity);
    Subdivision toEntity(SubdivisionDto dto);
}
