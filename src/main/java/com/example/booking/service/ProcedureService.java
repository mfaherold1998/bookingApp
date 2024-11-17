package com.example.booking.service;

import com.example.booking.common.*;
import com.example.booking.dto.ProcedureDto;
import com.example.booking.entity.Procedure;
import com.example.booking.mapper.ProcedureMapper;
import com.example.booking.repository.ProcedureRepository;
import org.springframework.stereotype.Service;

@Service
public class ProcedureService extends BaseService<Procedure, ProcedureDto, ProcedureMapper, ProcedureRepository> {
    protected ProcedureService(ProcedureRepository repository, ProcedureMapper mapper) {
        super(repository, mapper);
    }
}