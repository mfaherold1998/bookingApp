package com.example.booking.controller;

import com.example.booking.dto.JwtAuthResponseDto;
import com.example.booking.dto.SignInRequestDto;
import com.example.booking.dto.SignUpRequestDto;
import com.example.booking.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@Valid @NotNull @RequestBody SignUpRequestDto request) {
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@Valid @NotNull @RequestBody SignInRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @GetMapping("/refresh/{token}")
    public ResponseEntity<JwtAuthResponseDto> refresh(@NotNull @PathVariable String token) {
        return ResponseEntity.ok(authService.refresh(token));
    }
    @GetMapping("/activate_email/{code}")
    public ResponseEntity<Boolean> activateEmail(@NotNull @PathVariable String code) {
        return ResponseEntity.ok(authService.activateEmail(code));
    }
    @GetMapping("/send_verification_email/{email}")
    public ResponseEntity<Boolean> sendVerificationEmail(@NotNull @PathVariable String email) {
        return ResponseEntity.ok(authService.sendVerificationEmail(email));
    }
}
