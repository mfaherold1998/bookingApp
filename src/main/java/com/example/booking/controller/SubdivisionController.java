package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.SubdivisionDto;
import com.example.booking.service.SubdivisionService;
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
@RequestMapping("/api/subdivisions")
@Tag(name = "Subdivisions", description = "The Subdivisions API")
public class SubdivisionController extends BaseController<SubdivisionDto, SubdivisionService> {

    public SubdivisionController(SubdivisionService service) {
        super(service);
    }

    @Operation(
            summary = "Fetch all Subdivisions",
            description = "Fetches all Subdivision entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @Override
    public ResponseEntity<List<SubdivisionDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Fetch a Subdivision by Id",
            description = "Fetches a Subdivision entity and their data from data source by an specific Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @Override
    public ResponseEntity<SubdivisionDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Adds a Subdivision",
            description = "Adds a Subdivision to the list of Subdivisions in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a Subdivision"),
            @ApiResponse(responseCode = "500", description = "invalid Subdivision cannot be added")
    })
    @Override
    public ResponseEntity<SubdivisionDto> save(@NotNull @Valid @RequestBody SubdivisionDto dto) {
        return super.save(dto);
    }

    @Operation(
            summary = "Delete a Subdivision",
            description = "Delete a Subdivision by a specific id from the list of Subdivisions in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successfully deleted a Subdivision"),
            @ApiResponse(responseCode = "404", description = "there is not Subdivision with the given id")
    })
    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Update a Subdivision",
            description = "Update a Subdivision by a specific id from the list of Subdivisions in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated a Subdivision"),
            @ApiResponse(responseCode = "404", description = "there is not Subdivision with the given id")
    })
    @Override
    public ResponseEntity<SubdivisionDto> update(@NotNull @Valid @RequestBody SubdivisionDto dto) {
        return super.update(dto);
    }

}
