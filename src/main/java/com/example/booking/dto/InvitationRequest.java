package com.example.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationRequest {

    @NotNull
    private Long subdivision;

    @NotNull
    private Long hostProprietorId;

    @NotBlank
    private String invitedUserEmail;

}