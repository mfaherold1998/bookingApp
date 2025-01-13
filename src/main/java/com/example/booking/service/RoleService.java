package com.example.booking.service;

import com.example.booking.common.BaseService;
import com.example.booking.dto.RoleDto;
import com.example.booking.entity.RoleEntity;
import com.example.booking.mapper.RoleMapper;
import com.example.booking.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<RoleEntity, RoleDto, RoleMapper, RoleRepository> {

    protected RoleService(RoleRepository repository, RoleMapper mapper) {
        super(repository, mapper);
    }

    RoleEntity findByName(String roleName){
        return repository.findByName(roleName);
    }

}