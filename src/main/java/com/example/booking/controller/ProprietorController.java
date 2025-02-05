package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.ProprietorDto;
import com.example.booking.entity.Proprietor;
import com.example.booking.service.ProprietorService;
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
@RequestMapping("/api/proprietors")

@Tag(name = "Proprietors", description = "API for managing corporation owners information.")
public class ProprietorController extends BaseController<Proprietor, ProprietorDto, ProprietorService> {

    public ProprietorController(ProprietorService service) {
        super(service);
    }

    @Operation(
            summary = "Fetch all Proprietors",
            description = "Retrieves a list of all proprietors registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    public ResponseEntity<List<ProprietorDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Fetch a Proprietor by Id",
            description = "Retrieves the details of a specific proprietor using its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    public ResponseEntity<ProprietorDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Create a new Proprietor",
            description = "Creates a new proprietor in the system with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "500", description = "Bad Credentials")
    })
    @Override
    public ResponseEntity<ProprietorDto> save(@NotNull @Valid @RequestBody ProprietorDto dto) {
        return super.save(dto);
    }

    @Operation(
            summary = "Delete a Proprietor",
            description = "Marks a specific proprietor as deleted without permanently removing it from the system. The proprietor is flagged as inactive but remains in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Proprietor")
    })
    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Update a Proprietor",
            description = "Updates the details of an existing proprietor. Requires the unique ID of the proprietor and the new data to be saved.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Proprietor")
    })
    @Override
    public ResponseEntity<ProprietorDto> update(@NotNull @Valid @RequestBody ProprietorDto dto) {
        return super.update(dto);
    }

}
