package com.example.booking.repository;

import com.example.booking.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Optional<Invitation> findByToken(String token);
    Optional<Invitation> findByStatus(String status);
    Optional<Invitation> findByExpirationDate(LocalDateTime expDate);
    Optional<Invitation> findBySubdivisionIdAndUserId(Long subdivisionId, Long userId);

    void deleteByStatus(String email);
}