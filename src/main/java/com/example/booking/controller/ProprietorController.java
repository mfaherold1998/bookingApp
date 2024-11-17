package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.ProprietorDto;
import com.example.booking.service.ProprietorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/propietors")
public class ProprietorController extends BaseController<ProprietorDto, ProprietorService> {

    public ProprietorController(ProprietorService service) {
        super(service);
    }

}
