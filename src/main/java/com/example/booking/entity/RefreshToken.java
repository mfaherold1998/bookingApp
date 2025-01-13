package com.example.booking.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "RefreshToken")
//@SQLRestriction("deleted = false")
public class RefreshToken {

    @Id
    private String token;

    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private String email;
}
