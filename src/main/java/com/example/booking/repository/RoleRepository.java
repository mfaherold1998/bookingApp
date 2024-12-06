package com.example.booking.repository;

import com.example.booking.common.BaseRepository;
import com.example.booking.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role> {

    Role findByName(String name);
}
