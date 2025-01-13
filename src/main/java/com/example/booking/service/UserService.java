package com.example.booking.service;

import com.example.booking.common.BaseService;
import com.example.booking.dto.UserDto;
import com.example.booking.entity.UserEntity;
import com.example.booking.exception.NotFoundException;
import com.example.booking.mapper.UserMapper;
import com.example.booking.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<UserEntity, UserDto, UserMapper, UserRepository> {

    protected UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }

    public UserEntity findByEmail(String email){
        return repository.findByEmail(email).orElseThrow(
                () -> NotFoundException.builder()
                        .build());
    }
}
