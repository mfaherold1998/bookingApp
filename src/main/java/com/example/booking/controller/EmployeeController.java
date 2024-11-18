package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.EmployeeDto;
import com.example.booking.service.EmployeeService;
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
@RequestMapping("/api/employees")
@Tag(name = "Employees", description = "The Employees API")
public class EmployeeController extends BaseController<EmployeeDto, EmployeeService> {

    public EmployeeController(EmployeeService service) {
        super(service);
    }

    @Operation(
            summary = "Fetch all Employees",
            description = "Fetches all Employee entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @Override
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Fetch a Employee by Id",
            description = "Fetches a Employee entity and their data from data source by an specific Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @Override
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Adds a Employee",
            description = "Adds a Employee to the list of Employees in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a Employee"),
            @ApiResponse(responseCode = "500", description = "invalid Employee cannot be added")
    })
    @Override
    public ResponseEntity<EmployeeDto> save(@NotNull @Valid @RequestBody EmployeeDto dto) {
        return super.save(dto);
    }

    @Operation(
            summary = "Delete a Employee",
            description = "Delete a Employee by a specific id from the list of Employees in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successfully deleted a Employee"),
            @ApiResponse(responseCode = "404", description = "there is not Employee with the given id")
    })
    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Update a Employee",
            description = "Update a Employee by a specific id from the list of Employees in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated a Employee"),
            @ApiResponse(responseCode = "404", description = "there is not Employee with the given id")
    })
    @Override
    public ResponseEntity<EmployeeDto> update(@NotNull @Valid @RequestBody EmployeeDto dto) {
        return super.update(dto);
    }

}
