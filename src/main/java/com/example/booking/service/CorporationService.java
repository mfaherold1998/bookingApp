package com.example.booking.service;

import com.example.booking.common.*;
import com.example.booking.dto.CorporationDto;
import com.example.booking.entity.Corporation;
import com.example.booking.mapper.CorporationMapper;
import com.example.booking.repository.CorporationRepository;
import org.springframework.stereotype.Service;

@Service
public class CorporationService extends BaseService<Corporation, CorporationDto, CorporationMapper, CorporationRepository> {
    protected CorporationService(CorporationRepository repository, CorporationMapper mapper) {
        super(repository, mapper);
    }
}