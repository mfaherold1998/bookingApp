package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.EmployeeAgendaDto;
import com.example.booking.service.EmployeeAgendaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/agendas")
public class EmployeeAgendaController extends BaseController<EmployeeAgendaDto, EmployeeAgendaService> implements EmployeeAgendaApi{

    public EmployeeAgendaController(EmployeeAgendaService service) {
        super(service);
    }

    @Override
    public ResponseEntity<List<EmployeeAgendaDto>> getAll() {
        return super.getAll();
    }

    @Override
    public ResponseEntity<EmployeeAgendaDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    public ResponseEntity<EmployeeAgendaDto> save(@NotNull @Valid @RequestBody EmployeeAgendaDto dto) {
        return super.save(dto);
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    public ResponseEntity<EmployeeAgendaDto> update(@NotNull @Valid @RequestBody EmployeeAgendaDto dto) {
        return super.update(dto);
    }

}
