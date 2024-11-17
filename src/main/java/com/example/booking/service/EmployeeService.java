package com.example.booking.service;

import com.example.booking.common.*;
import com.example.booking.dto.EmployeeDto;
import com.example.booking.entity.Employee;
import com.example.booking.mapper.EmployeeMapper;
import com.example.booking.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends BaseService<Employee, EmployeeDto, EmployeeMapper, EmployeeRepository> {
    protected EmployeeService(EmployeeRepository repository, EmployeeMapper mapper) {
        super(repository, mapper);
    }
}