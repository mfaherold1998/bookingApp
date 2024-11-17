package com.example.booking.service;

import com.example.booking.common.*;
import com.example.booking.dto.ProprietorDto;
import com.example.booking.entity.Proprietor;
import com.example.booking.mapper.ProprietorMapper;
import com.example.booking.repository.ProprietorRepository;
import org.springframework.stereotype.Service;

@Service
public class ProprietorService extends BaseService<Proprietor, ProprietorDto, ProprietorMapper, ProprietorRepository> {
    protected ProprietorService(ProprietorRepository repository, ProprietorMapper mapper) {
        super(repository, mapper);
    }
}