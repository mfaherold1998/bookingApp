package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.BookingDto;
import com.example.booking.service.BookingService;
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
public class BookingController extends BaseController<BookingDto, BookingService> implements BookingApi{

    public BookingController(BookingService service) {
        super(service);
    }

    @Override
    public ResponseEntity<List<BookingDto>> getAll() {
        return super.getAll();
    }

    @Override
    public ResponseEntity<BookingDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    public ResponseEntity<BookingDto> save(@NotNull @Valid @RequestBody BookingDto dto) {
        return super.save(dto);
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    public ResponseEntity<BookingDto> update(@NotNull @Valid @RequestBody BookingDto dto) {
        return super.update(dto);
    }

}
