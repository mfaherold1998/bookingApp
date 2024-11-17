package com.example.booking.mapper;

import com.example.booking.dto.BookingDto;
import com.example.booking.common.BaseMapper;
import com.example.booking.entity.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper extends BaseMapper<BookingDto, Booking> {
    BookingDto toDto(Booking entity);
    Booking toEntity(BookingDto dto);
}
