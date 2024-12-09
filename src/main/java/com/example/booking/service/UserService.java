package com.example.booking.service;

import com.example.booking.common.BaseService;
import com.example.booking.dto.UserDto;
import com.example.booking.entity.User;
import com.example.booking.exception.NotFoundException;
import com.example.booking.mapper.UserMapper;
import com.example.booking.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, UserDto, UserMapper, UserRepository> {

    protected UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }

    public User findByUsername(String username){
        return repository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("Not Found Exception", "Object with given username does not exists"));
    }

    public User findByEmail(String email){
        return repository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Not Found Exception", "Object with given email does not exists"));
    }
}
