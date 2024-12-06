package com.example.booking.service;

import com.example.booking.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}