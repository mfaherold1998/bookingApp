package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.ClientDto;
import com.example.booking.service.ClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController extends BaseController<ClientDto, ClientService> implements ClientApi {

    public ClientController(ClientService service) {
        super(service);
    }

    @Override
    public ResponseEntity<List<ClientDto>> getAll() {
        return super.getAll();
    }

    @Override
    public ResponseEntity<ClientDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    public ResponseEntity<ClientDto> save(@NotNull @Valid @RequestBody ClientDto dto) {
        return super.save(dto);
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    public ResponseEntity<ClientDto> update(@NotNull @Valid @RequestBody ClientDto dto) {
        return super.update(dto);
    }

}
