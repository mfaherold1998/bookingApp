package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.CorporationDto;
import com.example.booking.service.CorporationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/corporations")
public class CorporationController extends BaseController<CorporationDto, CorporationService> {

    public CorporationController(CorporationService service) {
        super(service);
    }

}
