package com.example.booking.controller;

import com.example.booking.dto.InvitationRequest;
import com.example.booking.service.InvitationService;
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
@RequestMapping("/api/invitations")
@AllArgsConstructor
@Tag(name = "Invitations", description = "The Invitations API")
public class InvitationController {

    private final InvitationService invitationService;

    @Operation(
            summary = "Send an invitation",
            description = "Send an invitation to a current user of the platform to be part of the staff of an enterprise.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("/send")
    public ResponseEntity<Boolean> sendInvitation(@Valid @NotNull @RequestBody InvitationRequest request) {
        return ResponseEntity.ok(invitationService.sendInvitation(request));
    }

    @Operation(
            summary = "Accept an invitation",
            description = "The user accepts the invitation to work and the proprietor is notified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/accept/{token}")
    public ResponseEntity<Boolean> acceptInvitation(@Valid @NotNull @PathVariable String token) {
        return ResponseEntity.ok(invitationService.acceptInvitation(token));
    }

    @Operation(
            summary = "Reject an invitation",
            description = "The user rejects the invitation to work and the proprietor is notified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("/reject/{token}")
    public ResponseEntity<Boolean> rejectInvitation(@Valid @NotNull @PathVariable String token) {
        return ResponseEntity.ok(invitationService.rejectInvitation(token));
    }

}
