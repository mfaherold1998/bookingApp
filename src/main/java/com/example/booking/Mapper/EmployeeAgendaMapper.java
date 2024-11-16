package com.example.booking.Mapper;

import com.example.booking.dto.EmployeeAgendaDto;
import com.example.booking.common.BaseMapper;
import com.example.booking.entity.EmployeeAgenda;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeAgendaMapper extends BaseMapper<EmployeeAgendaDto, EmployeeAgenda> {
    EmployeeAgendaDto toDto(EmployeeAgenda entity);
    EmployeeAgenda toEntity(EmployeeAgendaDto dto);
}
