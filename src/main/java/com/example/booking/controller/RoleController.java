package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.RoleDto;
import com.example.booking.entity.RoleEntity;
import com.example.booking.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@Tag(name = "Roles", description = "The Roles API")
public class RoleController extends BaseController<RoleEntity, RoleDto, RoleService> {
    public RoleController(RoleService service) {super(service);}
}
