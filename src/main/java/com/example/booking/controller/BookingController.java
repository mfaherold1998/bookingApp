package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.BookingDto;
import com.example.booking.entity.Booking;
import com.example.booking.service.BookingService;
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
@RequestMapping("/api/bookings")
@Tag(name = "Bookings", description = "The Bookings API")
public class BookingController extends BaseController<Booking, BookingDto, BookingService> {

    public BookingController(BookingService service) {
        super(service);
    }

    @Operation(
            summary = "Fetch all Bookings",
            description = "Fetches all Booking entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @Override
    public ResponseEntity<List<BookingDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Fetch a Booking by Id",
            description = "Fetches a Booking entity and their data from data source by an specific Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @Override
    public ResponseEntity<BookingDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Adds a Booking",
            description = "Adds a Booking to the list of Bookings in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a Booking"),
            @ApiResponse(responseCode = "500", description = "invalid Booking cannot be added")
    })
    @Override
    public ResponseEntity<BookingDto> save(@NotNull @Valid @RequestBody BookingDto dto) {
        return super.save(dto);
    }

    @Operation(
            summary = "Delete a Booking",
            description = "Delete a Booking by a specific id from the list of Bookings in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successfully deleted a Booking"),
            @ApiResponse(responseCode = "404", description = "there is not Booking with the given id")
    })
    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Update a Booking",
            description = "Update a Booking by a specific id from the list of Bookings in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated a Booking"),
            @ApiResponse(responseCode = "404", description = "there is not Booking with the given id")
    })
    @Override
    public ResponseEntity<BookingDto> update(@NotNull @Valid @RequestBody BookingDto dto) {
        return super.update(dto);
    }

}
