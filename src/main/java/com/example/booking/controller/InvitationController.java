package com.example.booking.controller;

import com.example.booking.dto.InvitationRequest;
import com.example.booking.entity.Invitation;
import com.example.booking.entity.UserEntity;
import com.example.booking.exception.CustomException;
import com.example.booking.exception.NotFoundException;
import com.example.booking.service.InvitationService;
import com.example.booking.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Invitation invitation = invitationService.getInvitationRepository().findByToken(token).orElseThrow(NotFoundException::new);
        if(getAuthenticatedUser().getId().equals(invitation.getUser().getId())){
            return ResponseEntity.ok(invitationService.acceptInvitation(token));
        }
        else {
            throw new CustomException("You have not permission to accept this invitation", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(
            summary = "Reject an invitation",
            description = "The user rejects the invitation to work and the proprietor is notified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/reject/{token}")
    public ResponseEntity<Boolean> rejectInvitation(@Valid @NotNull @PathVariable String token) {
        Invitation invitation = invitationService.getInvitationRepository().findByToken(token).orElseThrow(NotFoundException::new);
        if(getAuthenticatedUser().getId().equals(invitation.getUser().getId())){
            return ResponseEntity.ok(invitationService.rejectInvitation(token));
        }
        else {
            throw new CustomException("You have not permission to reject this invitation", HttpStatus.UNAUTHORIZED);
        }
    }

    public UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }
}
