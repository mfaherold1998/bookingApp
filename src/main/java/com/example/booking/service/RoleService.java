package com.example.booking.service;

import com.example.booking.common.BaseService;
import com.example.booking.dto.RoleDto;
import com.example.booking.entity.Role;
import com.example.booking.mapper.RoleMapper;
import com.example.booking.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleService extends BaseService<Role, RoleDto, RoleMapper, RoleRepository> {

    protected RoleService(RoleRepository repository, RoleMapper mapper) {
        super(repository, mapper);
    }

    Role findByName(Set<String> roleName){
        return repository.findByName(roleName.toString());
    }

}
