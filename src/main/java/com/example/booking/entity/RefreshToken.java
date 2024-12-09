package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "RefreshToken")
@SQLRestriction("deleted = false")
public class RefreshToken extends BaseEntity {
    @Id
    private String token;

    private Date expirationDate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String email;
}