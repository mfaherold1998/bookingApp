package com.example.booking.controller;

import com.example.booking.dto.JwtAuthResponse;
import com.example.booking.dto.SignInRequest;
import com.example.booking.dto.SignUpRequest;
import com.example.booking.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor

@Tag(name = "Authentication", description = "The Authentication API")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Register an user",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@Valid @NotNull @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(
            summary = "Login a user",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @NotNull @RequestBody SignInRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(
            summary = "Refresh a token",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/refresh/{token}")
    public ResponseEntity<JwtAuthResponse> refresh(@NotNull @PathVariable String token) {
        return ResponseEntity.ok(authService.refresh(token));
    }

    @Operation(
            summary = "Verify email",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/activate_email/{code}")
    public ResponseEntity<Boolean> activateEmail(@NotNull @PathVariable String code) {
        return ResponseEntity.ok(authService.activateEmail(code));
    }

    @Operation(
            summary = "Verification code",
            description = "Sends a code for email verifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/send_verification_email/{email}")
    public ResponseEntity<Boolean> sendVerificationEmail(@NotNull @PathVariable String email) {
        return ResponseEntity.ok(authService.sendVerificationEmail(email));
    }
}
