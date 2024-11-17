package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.BookingDto;
import com.example.booking.service.BookingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController extends BaseController<BookingDto, BookingService> {

    public BookingController(BookingService service) {
        super(service);
    }

}
