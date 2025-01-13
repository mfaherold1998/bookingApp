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
@Tag(name = "EmployeeAgendas", description = "The EmployeeAgendas API")
public class EmployeeAgendaController extends BaseController<EmployeeAgenda, EmployeeAgendaDto, EmployeeAgendaService> {

    public EmployeeAgendaController(EmployeeAgendaService service) {
        super(service);
    }

    @Operation(
            summary = "Fetch all EmployeeAgendas",
            description = "Fetches all EmployeeAgenda entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @Override
    public ResponseEntity<List<EmployeeAgendaDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Fetch a EmployeeAgenda by Id",
            description = "Fetches a EmployeeAgenda entity and their data from data source by an specific Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @Override
    public ResponseEntity<EmployeeAgendaDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Adds a EmployeeAgenda",
            description = "Adds a EmployeeAgenda to the list of EmployeeAgendas in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a EmployeeAgenda"),
            @ApiResponse(responseCode = "500", description = "invalid EmployeeAgenda cannot be added")
    })
    @Override
    public ResponseEntity<EmployeeAgendaDto> save(@NotNull @Valid @RequestBody EmployeeAgendaDto dto) {
        return super.save(dto);
    }

    @Operation(
            summary = "Delete a EmployeeAgenda",
            description = "Delete a EmployeeAgenda by a specific id from the list of EmployeeAgendas in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successfully deleted a EmployeeAgenda"),
            @ApiResponse(responseCode = "404", description = "there is not EmployeeAgenda with the given id")
    })
    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Update a EmployeeAgenda",
            description = "Update a EmployeeAgenda by a specific id from the list of EmployeeAgendas in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated a EmployeeAgenda"),
            @ApiResponse(responseCode = "404", description = "there is not EmployeeAgenda with the given id")
    })
    @Override
    public ResponseEntity<EmployeeAgendaDto> update(@NotNull @Valid @RequestBody EmployeeAgendaDto dto) {
        return super.update(dto);
    }

}
