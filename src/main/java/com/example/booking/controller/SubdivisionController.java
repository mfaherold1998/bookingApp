package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.SubdivisionDto;
import com.example.booking.entity.Subdivision;
import com.example.booking.service.SubdivisionService;
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
@RequestMapping("/api/subdivisions")

@Tag(name = "Subdivisions", description = "API for managing subsidiaries of corporations information.")
public class SubdivisionController extends BaseController<Subdivision, SubdivisionDto, SubdivisionService> {

    public SubdivisionController(SubdivisionService service) {
        super(service);
    }

    @Operation(
            summary = "Fetch all Subdivisions",
            description = "Retrieves a list of all subdivisions registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    public ResponseEntity<List<SubdivisionDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Fetch a Subdivision by Id",
            description = "Retrieves the details of a specific subdivision using its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    public ResponseEntity<SubdivisionDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Create a new Subdivision",
            description = "Creates a new subdivision in the system with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "500", description = "Bad Credentials")
    })
    @Override
    public ResponseEntity<SubdivisionDto> save(@NotNull @Valid @RequestBody SubdivisionDto dto) {
        return super.save(dto);
    }

    @Operation(
            summary = "Delete a Subdivision",
            description = "Marks a specific subdivision as deleted without permanently removing it from the system. The subdivision is flagged as inactive but remains in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Subdivision")
    })
    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Update a Subdivision",
            description = "Updates the details of an existing subdivision. Requires the unique ID of the subdivision and the new data to be saved.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Subdivision")
    })
    @Override
    public ResponseEntity<SubdivisionDto> update(@NotNull @Valid @RequestBody SubdivisionDto dto) {
        return super.update(dto);
    }

}
