package com.example.booking.Mapper;

import com.example.booking.dto.CorporationDto;
import com.example.booking.common.BaseMapper;
import com.example.booking.entity.Corporation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CorporationMapper extends BaseMapper<CorporationDto, Corporation> {
    CorporationDto toDto(Corporation entity);
    Corporation toEntity(CorporationDto dto);
}
