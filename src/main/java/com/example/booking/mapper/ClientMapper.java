package com.example.booking.mapper;

import com.example.booking.dto.ClientDto;
import com.example.booking.common.BaseMapper;
import com.example.booking.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper extends BaseMapper<ClientDto, Client> {
    ClientDto toDto(Client entity);
    Client toEntity(ClientDto dto);
}
