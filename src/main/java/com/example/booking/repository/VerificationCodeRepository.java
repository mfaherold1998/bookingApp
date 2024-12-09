package com.example.booking.repository;

import com.example.booking.common.BaseRepository;
import com.example.booking.entity.VerificationCode;

import java.util.Optional;

public interface VerificationCodeRepository extends BaseRepository<VerificationCode> {
    Optional<VerificationCode> findByCode(String code);
    Optional<VerificationCode> findByEmail(String email);
    void deleteByEmail(String email);
}