package com.example.ProducerMessagingProject.user.service;

import com.example.ProducerMessagingProject.security.jwt.JwtResponse;
import com.example.ProducerMessagingProject.user.LoginDto;
import com.example.ProducerMessagingProject.user.dto.UserInputDto;
import com.example.ProducerMessagingProject.user.dto.UserOutputDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserOutputDto> findAll();
    UserOutputDto findById(UUID id);
    UserOutputDto create(UserInputDto dto);
    UserOutputDto update(UUID id, UserInputDto dto);
    void delete(UUID id);
    JwtResponse login(LoginDto dto);
}
