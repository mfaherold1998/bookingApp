package com.example.booking.Mapper;

import com.example.booking.dto.ProcedureDto;
import com.example.booking.common.BaseMapper;
import com.example.booking.entity.Procedure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProcedureMapper extends BaseMapper<ProcedureDto, Procedure> {
    ProcedureDto toDto(Procedure entity);
    Procedure toEntity(ProcedureDto dto);
}
