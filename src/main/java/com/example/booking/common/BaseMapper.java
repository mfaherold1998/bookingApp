package com.example.booking.common;


public interface BaseMapper <D extends BaseDto, E extends BaseEntity>{
    D toDto(E entity);
    E toEntity(D dto);
}
