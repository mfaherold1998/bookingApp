package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.UserDto;
import com.example.booking.entity.UserEntity;
import com.example.booking.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")

@Tag(name = "Users", description = "API for managing users information.")
public class UserController extends BaseController<UserEntity, UserDto, UserService> {
    public UserController(UserService service) {super(service);}
}