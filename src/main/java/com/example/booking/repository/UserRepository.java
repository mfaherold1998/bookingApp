package com.example.booking.repository;

import com.example.booking.common.BaseRepository;
import com.example.booking.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<UserEntity> {
    Optional<UserEntity> findByEmail(String email);
}
