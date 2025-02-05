package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.ProcedureDto;
import com.example.booking.entity.Procedure;
import com.example.booking.service.ProcedureService;
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
@RequestMapping("/api/procedures")

@Tag(name = "Procedures", description = "API for managing the corporation services information.")
public class ProcedureController extends BaseController<Procedure, ProcedureDto, ProcedureService> {

    public ProcedureController(ProcedureService service) {
        super(service);
    }

    @Operation(
            summary = "Fetch all Procedures",
            description = "Retrieves a list of all procedures registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    public ResponseEntity<List<ProcedureDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Fetch a Procedure by Id",
            description = "Retrieves the details of a specific procedure using its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    public ResponseEntity<ProcedureDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Create a new Procedure",
            description = "Creates a new procedure in the system with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "500", description = "Bad Request")
    })
    @Override
    public ResponseEntity<ProcedureDto> save(@NotNull @Valid @RequestBody ProcedureDto dto) {
        return super.save(dto);
    }

    @Operation(
            summary = "Delete a Procedure",
            description = "Marks a specific procedure as deleted without permanently removing it from the system. The procedure is flagged as inactive but remains in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Procedure")
    })
    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Update a Procedure",
            description = "Updates the details of an existing procedure. Requires the unique ID of the procedure and the new data to be saved.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Procedure")
    })
    @Override
    public ResponseEntity<ProcedureDto> update(@NotNull @Valid @RequestBody ProcedureDto dto) {
        return super.update(dto);
    }

}
