package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.ProcedureDto;
import com.example.booking.service.ProcedureService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/procedures")
public class ProcedureController extends BaseController<ProcedureDto, ProcedureService> implements ProcedureApi{

    public ProcedureController(ProcedureService service) {
        super(service);
    }

    @Override
    public ResponseEntity<List<ProcedureDto>> getAll() {
        return super.getAll();
    }

    @Override
    public ResponseEntity<ProcedureDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    public ResponseEntity<ProcedureDto> save(@NotNull @Valid @RequestBody ProcedureDto dto) {
        return super.save(dto);
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    public ResponseEntity<ProcedureDto> update(@NotNull @Valid @RequestBody ProcedureDto dto) {
        return super.update(dto);
    }

}
