package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.SubdivisionDto;
import com.example.booking.service.SubdivisionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subdivisions")
public class SubdivisionController extends BaseController<SubdivisionDto, SubdivisionService> {

    public SubdivisionController(SubdivisionService service) {
        super(service);
    }

}
