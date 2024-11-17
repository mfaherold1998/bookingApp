package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.EmployeeDto;
import com.example.booking.service.EmployeeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController extends BaseController<EmployeeDto, EmployeeService> {

    public EmployeeController(EmployeeService service) {
        super(service);
    }

}
