package com.example.booking.service;

import com.example.booking.common.*;
import com.example.booking.dto.ClientDto;
import com.example.booking.entity.Client;
import com.example.booking.mapper.ClientMapper;
import com.example.booking.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends BaseService<Client, ClientDto, ClientMapper, ClientRepository> {
    protected ClientService(ClientRepository repository, ClientMapper mapper) {
        super(repository, mapper);
    }
}
