package com.example.booking.controller;

import com.example.booking.dto.BookingDto;
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

@Tag(name = "Bookings", description = "The Bookings API")

public interface BookingApi {

    @Operation(
            summary = "Fetch all Bookings",
            description = "Fetches all Booking entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<List<BookingDto>> getAll();

    @Operation(
            summary = "Fetch a Booking by Id",
            description = "Fetches a Booking entity and their data from data source by an specific Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<BookingDto> getById(@PathVariable Long id);

    @Operation(
            summary = "Adds a Booking",
            description = "Adds a Booking to the list of Bookings in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a Booking"),
            @ApiResponse(responseCode = "500", description = "invalid Booking cannot be added")
    })
    ResponseEntity<BookingDto> save(@NotNull @Valid @RequestBody BookingDto dto);

    @Operation(
            summary = "Delete a Booking",
            description = "Delete a Booking by a specific id from the list of Bookings in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successfully deleted a Booking"),
            @ApiResponse(responseCode = "404", description = "there is not Booking with the given id")
    })
    ResponseEntity<Boolean> delete(@PathVariable Long id);

    @Operation(
            summary = "Update a Booking",
            description = "Update a Booking by a specific id from the list of Bookings in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated a Booking"),
            @ApiResponse(responseCode = "404", description = "there is not Booking with the given id")
    })
    ResponseEntity<BookingDto> update(@NotNull @Valid @RequestBody BookingDto dto);

}