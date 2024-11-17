package com.example.booking.controller;

import com.example.booking.dto.ProcedureDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Procedures", description = "The Procedures API")

public interface ProcedureApi {

    @Operation(
            summary = "Fetch all Procedures",
            description = "Fetches all Procedure entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<List<ProcedureDto>> getAll();

    @Operation(
            summary = "Fetch a Procedure by Id",
            description = "Fetches a Procedure entity and their data from data source by an specific Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<ProcedureDto> getById(@PathVariable Long id);

    @Operation(
            summary = "Adds a Procedure",
            description = "Adds a Procedure to the list of Procedures in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a Procedure"),
            @ApiResponse(responseCode = "500", description = "invalid Procedure cannot be added")
    })
    ResponseEntity<ProcedureDto> save(@NotNull @Valid @RequestBody ProcedureDto dto);

    @Operation(
            summary = "Delete a Procedure",
            description = "Delete a Procedure by a specific id from the list of Procedures in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successfully deleted a Procedure"),
            @ApiResponse(responseCode = "404", description = "there is not Procedure with the given id")
    })
    ResponseEntity<Boolean> delete(@PathVariable Long id);

    @Operation(
            summary = "Update a Procedure",
            description = "Update a Procedure by a specific id from the list of Procedures in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated a Procedure"),
            @ApiResponse(responseCode = "404", description = "there is not Procedure with the given id")
    })
    ResponseEntity<ProcedureDto> update(@NotNull @Valid @RequestBody ProcedureDto dto);

}