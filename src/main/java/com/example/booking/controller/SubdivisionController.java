package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.SubdivisionDto;
import com.example.booking.service.SubdivisionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subdivisions")
public class SubdivisionController extends BaseController<SubdivisionDto, SubdivisionService> implements SubdivisionApi{

    public SubdivisionController(SubdivisionService service) {
        super(service);
    }

    @Override
    public ResponseEntity<List<SubdivisionDto>> getAll() {
        return super.getAll();
    }

    @Override
    public ResponseEntity<SubdivisionDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    public ResponseEntity<SubdivisionDto> save(@NotNull @Valid @RequestBody SubdivisionDto dto) {
        return super.save(dto);
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    public ResponseEntity<SubdivisionDto> update(@NotNull @Valid @RequestBody SubdivisionDto dto) {
        return super.update(dto);
    }

}
