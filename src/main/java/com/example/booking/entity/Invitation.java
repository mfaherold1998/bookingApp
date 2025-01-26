package com.example.booking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "Invitations")
public class Invitation {

    @Id
    private String token;

    @OneToOne
    @JoinColumn(name = "subdivision_id", nullable = true, unique = true)
    private Subdivision subdivision;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private String status;

    private LocalDateTime expirationDate;
}