package com.example.booking.service;

import com.example.booking.common.*;
import com.example.booking.dto.BookingDto;
import com.example.booking.entity.Booking;
import com.example.booking.mapper.BookingMapper;
import com.example.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService extends BaseService<Booking, BookingDto, BookingMapper, BookingRepository> {
    protected BookingService(BookingRepository repository, BookingMapper mapper) {
        super(repository, mapper);
    }
}