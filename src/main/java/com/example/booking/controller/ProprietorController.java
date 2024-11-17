package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.ProprietorDto;
import com.example.booking.service.ProprietorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/propietors")
public class ProprietorController extends BaseController<ProprietorDto, ProprietorService> implements ProprietorApi{

    public ProprietorController(ProprietorService service) {
        super(service);
    }

    @Override
    public ResponseEntity<List<ProprietorDto>> getAll() {
        return super.getAll();
    }

    @Override
    public ResponseEntity<ProprietorDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    public ResponseEntity<ProprietorDto> save(@NotNull @Valid @RequestBody ProprietorDto dto) {
        return super.save(dto);
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    public ResponseEntity<ProprietorDto> update(@NotNull @Valid @RequestBody ProprietorDto dto) {
        return super.update(dto);
    }

}
