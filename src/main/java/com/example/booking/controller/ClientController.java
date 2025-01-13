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
@Tag(name = "Clients", description = "The Clients API")
public class ClientController extends BaseController<Client,ClientDto, ClientService> {

    public ClientController(ClientService service) {
        super(service);
    }

    @Operation(
            summary = "Fetch all Clients",
            description = "Fetches all Clients entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @Override
    public ResponseEntity<List<ClientDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Fetch a Client by Id",
            description = "Fetches a Client entity and their data from data source by an specific Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @Override
    public ResponseEntity<ClientDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Adds a Client",
            description = "Adds a Client to the list of Clients in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a Client"),
            @ApiResponse(responseCode = "500", description = "invalid Client cannot be added")
    })
    @Override
    public ResponseEntity<ClientDto> save(@NotNull @Valid @RequestBody ClientDto dto) {
        return super.save(dto);
    }

    @Operation(
            summary = "Delete a Client",
            description = "Delete a Client by a specific id from the list of Clients in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successfully deleted a Client"),
            @ApiResponse(responseCode = "404", description = "there is not Client with the given id")
    })
    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Update a Client",
            description = "Update a Client by a specific id from the list of Clients in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated a Client"),
            @ApiResponse(responseCode = "404", description = "there is not Client with the given id")
    })
    @Override
    public ResponseEntity<ClientDto> update(@NotNull @Valid @RequestBody ClientDto dto) {
        return super.update(dto);
    }

}
