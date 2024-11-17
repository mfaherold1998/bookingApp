package com.example.booking.service;

import com.example.booking.common.*;
import com.example.booking.dto.EmployeeAgendaDto;
import com.example.booking.entity.EmployeeAgenda;
import com.example.booking.mapper.EmployeeAgendaMapper;
import com.example.booking.repository.EmployeeAgendaRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeAgendaService extends BaseService<EmployeeAgenda, EmployeeAgendaDto, EmployeeAgendaMapper, EmployeeAgendaRepository> {
    protected EmployeeAgendaService(EmployeeAgendaRepository repository, EmployeeAgendaMapper mapper) {
        super(repository, mapper);
    }
}