package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.ProcedureDto;
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
@Tag(name = "Procedures", description = "The Procedures API")
public class ProcedureController extends BaseController<ProcedureDto, ProcedureService> {

    public ProcedureController(ProcedureService service) {
        super(service);
    }

    @Operation(
            summary = "Fetch all Procedures",
            description = "Fetches all Procedure entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @Override
    public ResponseEntity<List<ProcedureDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Fetch a Procedure by Id",
            description = "Fetches a Procedure entity and their data from data source by an specific Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @Override
    public ResponseEntity<ProcedureDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Adds a Procedure",
            description = "Adds a Procedure to the list of Procedures in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a Procedure"),
            @ApiResponse(responseCode = "500", description = "invalid Procedure cannot be added")
    })
    @Override
    public ResponseEntity<ProcedureDto> save(@NotNull @Valid @RequestBody ProcedureDto dto) {
        return super.save(dto);
    }

    @Operation(
            summary = "Delete a Procedure",
            description = "Delete a Procedure by a specific id from the list of Procedures in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successfully deleted a Procedure"),
            @ApiResponse(responseCode = "404", description = "there is not Procedure with the given id")
    })
    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Update a Procedure",
            description = "Update a Procedure by a specific id from the list of Procedures in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated a Procedure"),
            @ApiResponse(responseCode = "404", description = "there is not Procedure with the given id")
    })
    @Override
    public ResponseEntity<ProcedureDto> update(@NotNull @Valid @RequestBody ProcedureDto dto) {
        return super.update(dto);
    }

}
