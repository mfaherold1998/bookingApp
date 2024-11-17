package com.example.booking.mapper;

import com.example.booking.dto.EmployeeDto;
import com.example.booking.common.BaseMapper;
import com.example.booking.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends BaseMapper<EmployeeDto, Employee> {
    EmployeeDto toDto(Employee entity);
    Employee toEntity(EmployeeDto dto);
}
