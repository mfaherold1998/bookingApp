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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Bookings", description = "API for scheduling and managing appointments within the company.")
public class BookingController extends BaseController<Booking, BookingDto, BookingService> {

    public BookingController(BookingService service) {
        super(service);
    }

    @Operation(
            summary = "Fetch all Bookings",
            description = "Retrieves a list of all appointments booked within the company.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public ResponseEntity<List<BookingDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Fetch a Booking by Id",
            description = "Retrieves the details of a specific appointment using its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    @PreAuthorize("hasAnyAuthority('CLIENT', 'EMPLOYEE', 'PROPRIETOR','SUPERADMIN')")
    public ResponseEntity<BookingDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Create a new Booking",
            description = "Creates a new appointment in the system with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "500", description = "Invalid Booking Credentials")
    })
    @Override
    @PreAuthorize("hasAnyAuthority('CLIENT', 'EMPLOYEE', 'PROPRIETOR','SUPERADMIN')")
    public ResponseEntity<BookingDto> save(@NotNull @Valid @RequestBody BookingDto dto) {
        return super.save(dto);
    }

    @Operation(
            summary = "Delete a Booking",
            description = "Marks a specific appointment as deleted without permanently removing it from the system. The booking is flagged as inactive but remains in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Booking")
    })
    @Override
    @PreAuthorize("hasAnyAuthority('CLIENT', 'EMPLOYEE', 'PROPRIETOR','SUPERADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Update a Booking",
            description = "Updates the details of an existing appointment. Requires the unique ID of the booking and the new data to be saved.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Booking")
    })
    @Override
    @PreAuthorize("hasAnyAuthority('CLIENT', 'EMPLOYEE', 'PROPRIETOR','SUPERADMIN')")
    public ResponseEntity<BookingDto> update(@NotNull @Valid @RequestBody BookingDto dto) {
        return super.update(dto);
    }

}
