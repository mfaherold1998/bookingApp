package com.example.booking.controller;

import com.example.booking.dto.SubdivisionDto;
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

@Tag(name = "Subdivisions", description = "The Subdivisions API")

public interface SubdivisionApi {

    @Operation(
            summary = "Fetch all Subdivisions",
            description = "Fetches all Subdivision entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<List<SubdivisionDto>> getAll();

    @Operation(
            summary = "Fetch a Subdivision by Id",
            description = "Fetches a Subdivision entity and their data from data source by an specific Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<SubdivisionDto> getById(@PathVariable Long id);

    @Operation(
            summary = "Adds a Subdivision",
            description = "Adds a Subdivision to the list of Subdivisions in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a Subdivision"),
            @ApiResponse(responseCode = "500", description = "invalid Subdivision cannot be added")
    })
    ResponseEntity<SubdivisionDto> save(@NotNull @Valid @RequestBody SubdivisionDto dto);

    @Operation(
            summary = "Delete a Subdivision",
            description = "Delete a Subdivision by a specific id from the list of Subdivisions in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successfully deleted a Subdivision"),
            @ApiResponse(responseCode = "404", description = "there is not Subdivision with the given id")
    })
    ResponseEntity<Boolean> delete(@PathVariable Long id);

    @Operation(
            summary = "Update a Subdivision",
            description = "Update a Subdivision by a specific id from the list of Subdivisions in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated a Subdivision"),
            @ApiResponse(responseCode = "404", description = "there is not Subdivision with the given id")
    })
    ResponseEntity<SubdivisionDto> update(@NotNull @Valid @RequestBody SubdivisionDto dto);

}