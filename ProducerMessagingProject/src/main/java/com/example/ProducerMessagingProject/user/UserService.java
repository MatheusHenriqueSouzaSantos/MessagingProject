package com.example.ProducerMessagingProject.user;

import com.example.ProducerMessagingProject.JwtResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserOutputDto> findAll();
    UserOutputDto findById(UUID id);
    UserOutputDto create(UserInputDto dto);
    UserOutputDto update(UUID id, UserInputDto dto);
    void delete(UUID id);
    JwtResponse login(UserInputDto dto);
}
