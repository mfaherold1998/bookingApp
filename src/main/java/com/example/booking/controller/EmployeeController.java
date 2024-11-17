package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.EmployeeDto;
import com.example.booking.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController extends BaseController<EmployeeDto, EmployeeService> implements EmployeeApi{

    public EmployeeController(EmployeeService service) {
        super(service);
    }

    @Override
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return super.getAll();
    }

    @Override
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    public ResponseEntity<EmployeeDto> save(@NotNull @Valid @RequestBody EmployeeDto dto) {
        return super.save(dto);
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    public ResponseEntity<EmployeeDto> update(@NotNull @Valid @RequestBody EmployeeDto dto) {
        return super.update(dto);
    }

}
