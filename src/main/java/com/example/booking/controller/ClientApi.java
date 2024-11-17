package com.example.booking.controller;

import com.example.booking.dto.ClientDto;
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

@Tag(name = "Clients", description = "The Clients API")

public interface ClientApi {

    @Operation(
            summary = "Fetch all Clients",
            description = "Fetches all Clients entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<List<ClientDto>> getAll();

    @Operation(
            summary = "Fetch a Client by Id",
            description = "Fetches a Client entity and their data from data source by an specific Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<ClientDto> getById(@PathVariable Long id);

    @Operation(
            summary = "Adds a Client",
            description = "Adds a Client to the list of Clients in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a Client"),
            @ApiResponse(responseCode = "500", description = "invalid Client cannot be added")
    })
    ResponseEntity<ClientDto> save(@NotNull @Valid @RequestBody ClientDto dto);

    @Operation(
            summary = "Delete a Client",
            description = "Delete a Client by a specific id from the list of Clients in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successfully deleted a Client"),
            @ApiResponse(responseCode = "404", description = "there is not Client with the given id")
    })
    ResponseEntity<Boolean> delete(@PathVariable Long id);

    @Operation(
            summary = "Update a Client",
            description = "Update a Client by a specific id from the list of Clients in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated a Client"),
            @ApiResponse(responseCode = "404", description = "there is not Client with the given id")
    })
    ResponseEntity<ClientDto> update (@NotNull @Valid @RequestBody ClientDto dto);

}

