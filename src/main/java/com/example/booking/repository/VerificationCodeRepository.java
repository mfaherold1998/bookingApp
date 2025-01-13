package com.example.booking.repository;

import com.example.booking.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByCode(String code);
    Optional<VerificationCode> findByEmail(String email);
    void deleteByEmail(String email);
}
