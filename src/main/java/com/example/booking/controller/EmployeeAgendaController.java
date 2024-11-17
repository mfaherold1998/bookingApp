package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.EmployeeAgendaDto;
import com.example.booking.service.EmployeeAgendaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agendas")
public class EmployeeAgendaController extends BaseController<EmployeeAgendaDto, EmployeeAgendaService> {

    public EmployeeAgendaController(EmployeeAgendaService service) {
        super(service);
    }

}
