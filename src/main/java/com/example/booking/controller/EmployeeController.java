package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.EmployeeDto;
import com.example.booking.entity.Employee;
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

@Tag(name = "Employees", description = "API for managing employee information.")
public class EmployeeController extends BaseController<Employee, EmployeeDto, EmployeeService> {

    public EmployeeController(EmployeeService service) {
        super(service);
    }

    @Operation(
            summary = "Fetch all Employees",
            description = "Retrieves a list of all employee registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Fetch a Employee by Id",
            description = "Retrieves the details of a specific employee using its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Create a new Employee",
            description = "Creates a new employee in the system with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "500", description = "Bad Credentials")
    })
    @Override
    public ResponseEntity<EmployeeDto> save(@NotNull @Valid @RequestBody EmployeeDto dto) {
        return super.save(dto);
    }

    @Operation(
            summary = "Delete a Employee",
            description = "Marks a specific employee as deleted without permanently removing it from the system. The employee is flagged as inactive but remains in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Employee")
    })
    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Update a Employee",
            description = "Updates the details of an existing employee. Requires the unique ID of the employee and the new data to be saved.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Employee")
    })
    @Override
    public ResponseEntity<EmployeeDto> update(@NotNull @Valid @RequestBody EmployeeDto dto) {
        return super.update(dto);
    }

}
