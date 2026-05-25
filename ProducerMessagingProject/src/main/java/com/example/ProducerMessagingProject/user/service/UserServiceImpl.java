package com.example.ProducerMessagingProject.user.service;

import com.example.ProducerMessagingProject.exception.BusinessException;
import com.example.ProducerMessagingProject.security.jwt.JwtGenerate;
import com.example.ProducerMessagingProject.security.jwt.JwtResponse;
import com.example.ProducerMessagingProject.user.*;
import com.example.ProducerMessagingProject.user.dto.SendEmailDto;
import com.example.ProducerMessagingProject.user.dto.UserInputDto;
import com.example.ProducerMessagingProject.user.dto.UserOutputDto;
import com.example.ProducerMessagingProject.user.entity.UserModel;
import com.example.ProducerMessagingProject.user.event.SendEmailEvent;
import com.example.ProducerMessagingProject.user.messaging.TypeMessage;
import com.example.ProducerMessagingProject.user.messaging.UserProducerMessaging;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    private UserProducerMessaging producerMessaging;

    private JwtGenerate jwtGenerate;

    private PasswordEncoder passwordEncoder;

    private ApplicationEventPublisher eventPublisher;

    public UserServiceImpl(UserRepository repository, UserProducerMessaging producerMessaging, JwtGenerate jwtGenerate,
                           PasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.producerMessaging = producerMessaging;
        this.jwtGenerate = jwtGenerate;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserOutputDto> findAll() {
        return repository.findAll().stream().map(this::entityToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserOutputDto findById(UUID id) {
        UserModel user= repository.findById(id)
                .orElseThrow(()->new BusinessException("user not found", HttpStatus.NOT_FOUND));
        return entityToDto(user);
    }

    @Override
    @Transactional
    public UserOutputDto create(UserInputDto dto) {
        if(repository.existsByUserName(dto.userName())){
            throw new BusinessException("the provided user name already is used",HttpStatus.BAD_REQUEST);
        }
        if(repository.existsByEmail(dto.email())){
            throw new BusinessException("the provided email already is used",HttpStatus.BAD_REQUEST);
        }
        UserModel user=repository.save(new UserModel(dto.userName(), dto.email(), passwordEncoder.encode(dto.password())));
        eventPublisher.publishEvent(new SendEmailEvent(new SendEmailDto(dto.email(), TypeMessage.CREATION_ACCOUNT,"User created")));
        return entityToDto(user);
    }

    @Override
    @Transactional
    public UserOutputDto update(UUID id, UserInputDto dto) {
        UserModel userFromDataBase=repository.findById(id)
                .orElseThrow(()->new BusinessException("User not found",HttpStatus.NOT_FOUND));

        if(repository.existsByUserNameAndIdNot(dto.userName(),id)){
            throw new BusinessException("the provided email already is used",HttpStatus.BAD_REQUEST);
        }

        if(repository.existsByEmailAndIdNot(dto.email(),id)){
            throw new BusinessException("the provided email already is used",HttpStatus.BAD_REQUEST);
        }
        userFromDataBase.setUserName(dto.userName());
        userFromDataBase.setEmail(dto.email());
        userFromDataBase.setPassword(passwordEncoder.encode(dto.password()));
        eventPublisher.publishEvent(new SendEmailEvent(new SendEmailDto(dto.email(),TypeMessage.CREATION_ACCOUNT,"User updated")));
        return entityToDto(userFromDataBase);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UserModel user=repository.findById(id)
                        .orElseThrow(()->new BusinessException("User not found",HttpStatus.NOT_FOUND));
        repository.deleteById(id);
        eventPublisher.publishEvent(new SendEmailEvent(new SendEmailDto(user.getEmail(),TypeMessage.CREATION_ACCOUNT,"User deleted")));
    }

    @Override
    public JwtResponse login(LoginDto dto) {
        UserModel user=repository.findByEmail(dto.email())
                .orElseThrow(()->new BusinessException("email or password invalid",HttpStatus.BAD_REQUEST));
        if(!passwordEncoder.matches(dto.password(), user.getPassword())){
            throw new BusinessException("email or password invalid",HttpStatus.BAD_REQUEST);
        }
        return new JwtResponse(jwtGenerate.generateToken(user));
    }

    private UserOutputDto entityToDto(UserModel user){
        return new UserOutputDto(user.getUserName(), user.getEmail());
    }


}
