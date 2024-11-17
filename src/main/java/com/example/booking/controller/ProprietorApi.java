package com.example.booking.controller;

import com.example.booking.dto.ProprietorDto;
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

@Tag(name = "Proprietors", description = "The Proprietors API")

public interface ProprietorApi {

    @Operation(
            summary = "Fetch all Proprietors",
            description = "Fetches all Proprietor entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<List<ProprietorDto>> getAll();

    @Operation(
            summary = "Fetch a Proprietor by Id",
            description = "Fetches a Proprietor entity and their data from data source by an specific Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<ProprietorDto> getById(@PathVariable Long id);

    @Operation(
            summary = "Adds a Proprietor",
            description = "Adds a Proprietor to the list of Proprietors in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a Proprietor"),
            @ApiResponse(responseCode = "500", description = "invalid Proprietor cannot be added")
    })
    ResponseEntity<ProprietorDto> save(@NotNull @Valid @RequestBody ProprietorDto dto);

    @Operation(
            summary = "Delete a Proprietor",
            description = "Delete a Proprietor by a specific id from the list of Proprietors in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successfully deleted a Proprietor"),
            @ApiResponse(responseCode = "404", description = "there is not Proprietor with the given id")
    })
    ResponseEntity<Boolean> delete(@PathVariable Long id);

    @Operation(
            summary = "Update a Proprietor",
            description = "Update a Proprietor by a specific id from the list of Proprietors in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated a Proprietor"),
            @ApiResponse(responseCode = "404", description = "there is not Proprietor with the given id")
    })
    ResponseEntity<ProprietorDto> update(@NotNull @Valid @RequestBody ProprietorDto dto);

}