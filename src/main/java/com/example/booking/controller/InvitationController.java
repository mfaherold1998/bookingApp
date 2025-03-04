package com.example.booking.controller;

import com.example.booking.dto.InvitationRequest;
import com.example.booking.entity.Invitation;
import com.example.booking.entity.UserEntity;
import com.example.booking.exception.CustomException;
import com.example.booking.exception.NotFoundException;
import com.example.booking.service.InvitationService;
import com.example.booking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor

@Tag(name = "Invitations", description = "API for managing invitations to work operations")
public class InvitationController {

    private final InvitationService invitationService;
    private final UserService userService;

    @Operation(
            summary = "Send an invitation",
            description = "Send an invitation to a current user of the platform to be part of the staff of an enterprise.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @PostMapping("/send")
    @PreAuthorize("#request.hostProprietorId == authentication.principal.proprietor.id")
    public ResponseEntity<Boolean> sendInvitation(@Valid @NotNull @RequestBody InvitationRequest request) {

       //if(getAuthenticatedUser().getProprietor().getId().equals(request.getHostProprietorId())) {
        return ResponseEntity.ok(invitationService.sendInvitation(request));
       /* }
        else {
            throw new CustomException("You have not permission to send this invitation", HttpStatus.UNAUTHORIZED);
        }*/
    }

    @Operation(
            summary = "Accept an invitation",
            description = "The user accepts the invitation to work and the owner is notified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
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
            description = "The user rejects the invitation to work and the owner is notified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
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
