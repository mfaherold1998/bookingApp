package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.ClientDto;
import com.example.booking.service.ClientService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientController extends BaseController<ClientDto, ClientService> {

    public ClientController(ClientService service) {
        super(service);
    }

}
