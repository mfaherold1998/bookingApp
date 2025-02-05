package com.example.booking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "RefreshToken")
public class RefreshToken {

    @Id
    private String token;

    private Date expirationDate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private String email;
}
