package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.CorporationDto;
import com.example.booking.service.CorporationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/corporations")
public class CorporationController extends BaseController<CorporationDto, CorporationService> implements CorporationApi {

    public CorporationController(CorporationService service) {
        super(service);
    }

    @Override
    public ResponseEntity<List<CorporationDto>> getAll() {
        return super.getAll();
    }

    @Override
    public ResponseEntity<CorporationDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    public ResponseEntity<CorporationDto> save(@NotNull @Valid @RequestBody CorporationDto dto) {
        return super.save(dto);
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    public ResponseEntity<CorporationDto> update(@NotNull @Valid @RequestBody CorporationDto dto) {
        return super.update(dto);
    }

}
