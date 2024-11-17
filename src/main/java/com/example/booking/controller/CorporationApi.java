package com.example.booking.controller;

import com.example.booking.dto.CorporationDto;
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

@Tag(name = "Corporations", description = "The Corporations API")

public interface CorporationApi {

    @Operation(
            summary = "Fetch all Corporations",
            description = "Fetches all Corporation entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<List<CorporationDto>> getAll();

    @Operation(
            summary = "Fetch a Corporation by Id",
            description = "Fetches a Corporation entity and their data from data source by an specific Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<CorporationDto> getById(@PathVariable Long id);

    @Operation(
            summary = "Adds a Corporation",
            description = "Adds a Corporation to the list of Corporations in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a Corporation"),
            @ApiResponse(responseCode = "500", description = "invalid Corporation cannot be added")
    })
    ResponseEntity<CorporationDto> save(@NotNull @Valid @RequestBody CorporationDto dto);

    @Operation(
            summary = "Delete a Corporation",
            description = "Delete a Corporation by a specific id from the list of Corporations in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successfully deleted a Corporation"),
            @ApiResponse(responseCode = "404", description = "there is not Corporation with the given id")
    })
    ResponseEntity<Boolean> delete(@PathVariable Long id);

    @Operation(
            summary = "Update a Corporation",
            description = "Update a Corporation by a specific id from the list of Corporations in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated a Corporation"),
            @ApiResponse(responseCode = "404", description = "there is not Corporation with the given id")
    })
    ResponseEntity<CorporationDto> update(@NotNull @Valid @RequestBody CorporationDto dto);

}