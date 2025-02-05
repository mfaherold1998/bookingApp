package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.ClientDto;
import com.example.booking.entity.Client;
import com.example.booking.service.ClientService;
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
@RequestMapping("/api/clients")

@Tag(name = "Clients", description = "API for managing client information.")
public class ClientController extends BaseController<Client,ClientDto, ClientService> {

    public ClientController(ClientService service) {
        super(service);
    }

    @Operation(
            summary = "Fetch all Clients",
            description = "Retrieves a list of all clients registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    public ResponseEntity<List<ClientDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Fetch a Client by Id",
            description = "Retrieves the details of a specific client using its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    public ResponseEntity<ClientDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Create a new Client",
            description = "Creates a new client in the system with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "500", description = "Bad Credentials")
    })
    @Override
    public ResponseEntity<ClientDto> save(@NotNull @Valid @RequestBody ClientDto dto) {
        return super.save(dto);
    }

    @Operation(
            summary = "Delete a Client",
            description = "Marks a specific client as deleted without permanently removing it from the system. The client is flagged as inactive but remains in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Client")
    })
    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Update a Client",
            description = "Updates the details of an existing client. Requires the unique ID of the client and the new data to be saved.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Client")
    })
    @Override
    public ResponseEntity<ClientDto> update(@NotNull @Valid @RequestBody ClientDto dto) {
        return super.update(dto);
    }

}
