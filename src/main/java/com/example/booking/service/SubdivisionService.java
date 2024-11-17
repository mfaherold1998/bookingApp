package com.example.booking.service;

import com.example.booking.common.*;
import com.example.booking.dto.SubdivisionDto;
import com.example.booking.entity.Subdivision;
import com.example.booking.mapper.SubdivisionMapper;
import com.example.booking.repository.SubdivisionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubdivisionService extends BaseService<Subdivision, SubdivisionDto, SubdivisionMapper, SubdivisionRepository> {
    protected SubdivisionService(SubdivisionRepository repository, SubdivisionMapper mapper) {
        super(repository, mapper);
    }
}