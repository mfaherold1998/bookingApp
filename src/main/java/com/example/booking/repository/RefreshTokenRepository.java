package com.example.booking.repository;

import com.example.booking.common.BaseRepository;
import com.example.booking.entity.RefreshToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends BaseRepository<RefreshToken> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByEmail(String email);
    void deleteByEmail(String email);
}