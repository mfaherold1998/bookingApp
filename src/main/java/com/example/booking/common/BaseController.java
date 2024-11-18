package com.example.booking.common;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseController<D extends BaseDto, S extends BaseService<?, D, ?, ?>> {

    protected final S service;

    @PostMapping
    public ResponseEntity<D> save(@NotNull @Valid @RequestBody D dto) {
        return new ResponseEntity<>(service.saveDto(dto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<D> getById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getDtoById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<D>> getAll(){
        return new ResponseEntity<>(service.getAllDto(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<D> update(@NotNull @Valid @RequestBody D dto) {
        return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
