package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.ProcedureDto;
import com.example.booking.service.ProcedureService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/procedures")
public class ProcedureController extends BaseController<ProcedureDto, ProcedureService> {

    public ProcedureController(ProcedureService service) {
        super(service);
    }

}
