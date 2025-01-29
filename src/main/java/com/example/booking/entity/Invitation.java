package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "Invitations")
@SQLRestriction("deleted = false")
public class Invitation extends BaseEntity {

    @Id
    private String token;

    @OneToOne
    @JoinColumn(name = "subdivision_id", nullable = false)
    private Subdivision subdivision;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "proprietor_id"/*, nullable = false*/)
    private Proprietor proprietor;

    private String status;

    private LocalDateTime expirationDate;
}