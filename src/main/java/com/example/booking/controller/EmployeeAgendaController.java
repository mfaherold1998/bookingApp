package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.EmployeeAgendaDto;
import com.example.booking.entity.EmployeeAgenda;
import com.example.booking.service.EmployeeAgendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Employee Agendas", description = "API for scheduling and managing the employees agendas within the company.")
public class EmployeeAgendaController extends BaseController<EmployeeAgenda, EmployeeAgendaDto, EmployeeAgendaService> {

    public EmployeeAgendaController(EmployeeAgendaService service) {
        super(service);
    }

    @Operation(
            summary = "Fetch all Employee Agendas",
            description = "Retrieves a list of all employee agendas registered in the company.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    public ResponseEntity<List<EmployeeAgendaDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Fetch a Employee Agenda by Id",
            description = "Retrieves the details of a specific agenda using its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    public ResponseEntity<EmployeeAgendaDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Create a new Employee Agenda",
            description = "Creates a new agenda in the system with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "500", description = "Bad Credentials")
    })
    @Override
    public ResponseEntity<EmployeeAgendaDto> save(@NotNull @Valid @RequestBody EmployeeAgendaDto dto) {
        return super.save(dto);
    }

    @Operation(
            summary = "Delete a Employee Agenda",
            description = "Marks a specific agenda as deleted without permanently removing it from the system. The agenda is flagged as inactive but remains in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Employee Agenda")
    })
    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Update a EmployeeAgenda",
            description = "Updates the details of an existing agenda. Requires the unique ID of the agenda and the new data to be saved.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Employee Agenda")
    })
    @Override
    public ResponseEntity<EmployeeAgendaDto> update(@NotNull @Valid @RequestBody EmployeeAgendaDto dto) {
        return super.update(dto);
    }

}
