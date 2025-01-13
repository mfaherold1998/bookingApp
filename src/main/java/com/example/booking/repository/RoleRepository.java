package com.example.booking.repository;

import com.example.booking.common.BaseRepository;
import com.example.booking.entity.RoleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<RoleEntity> {
    RoleEntity findByName(String name);
}
