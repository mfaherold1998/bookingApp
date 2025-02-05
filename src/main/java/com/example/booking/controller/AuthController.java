package com.example.booking.controller;

import com.example.booking.dto.JwtAuthResponse;
import com.example.booking.dto.SignInRequest;
import com.example.booking.dto.SignUpRequest;
import com.example.booking.entity.UserEntity;
import com.example.booking.entity.VerificationCode;
import com.example.booking.exception.CustomException;
import com.example.booking.exception.NotFoundException;
import com.example.booking.repository.VerificationCodeRepository;
import com.example.booking.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

@Tag(name = "Authentication", description = "API responsible for user authentication and access token management.")
public class AuthController {

    private final AuthService authService;
    private final VerificationCodeRepository verificationCodeRepository;

    @Operation(
            summary = "Register an user",
            description = "Sing up operation for a new Client or Owner on the platform independently of any specific corporation, requiring valid user details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "Email Already Exists"),
    })
    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@Valid @NotNull @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(
            summary = "Login a user",
            description = "Sing in operation for existent users in the platform, generate access token, refresh token and expiration date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "403", description = "Bad Credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @NotNull @RequestBody SignInRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(
            summary = "Refresh access token",
            description = "Generate a new access token using a valid refresh token. This allows users to maintain authentication without re-entering credentials, enhancing security and user experience.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Token"),
            @ApiResponse(responseCode = "403", description = "Token Expired")
    })
    @GetMapping("/refresh/{token}")
    public ResponseEntity<JwtAuthResponse> refresh(@NotNull @PathVariable String token) {
        return ResponseEntity.ok(authService.refresh(token));
    }

    @Operation(
            summary = "Verify user email",
            description = "Validates a user's email address using a verification token sent via email. This step confirms ownership and may be required to activate the account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "403", description = "No Registered User"),
            @ApiResponse(responseCode = "401", description = "Unauthorized User")
    })
    @GetMapping("/activate_email/{code}")
    public ResponseEntity<Boolean> activateEmail(@NotNull @PathVariable String code) {

        VerificationCode verificationCode = verificationCodeRepository.findByCode(code).orElseThrow(NotFoundException::new);

        if(getAuthenticatedUser().getId().equals(verificationCode.getUser().getId())){
            return ResponseEntity.ok(authService.activateEmail(code));
        }
        else {
            throw new CustomException("You have not permission to verify this email", HttpStatus.UNAUTHORIZED);
        }

    }


    /*@Operation(
            summary = "Verification email",
            description = "Sends an email containing a unique verification code to the user. This code is required to verify the account and complete the registration process.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/send_verification_email/{email}")
    public ResponseEntity<Boolean> sendVerificationEmail(@NotNull @PathVariable String email) {
        return ResponseEntity.ok(authService.sendVerificationEmail(email));
    }*/

    public UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }
}
