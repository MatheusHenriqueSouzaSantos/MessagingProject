package com.example.ProducerMessagingProject.user;

import com.example.ProducerMessagingProject.BusinessException;
import com.example.ProducerMessagingProject.JwtGenerate;
import com.example.ProducerMessagingProject.JwtResponse;
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

    public UserServiceImpl(UserRepository repository, UserProducerMessaging producerMessaging, JwtGenerate jwtGenerate, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.producerMessaging = producerMessaging;
        this.jwtGenerate = jwtGenerate;
        this.passwordEncoder = passwordEncoder;
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
        producerMessaging.sendEmailMessage(dto.email(),"User created");
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
        producerMessaging.sendEmailMessage(dto.email(),"User updated");
        return entityToDto(userFromDataBase);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UserModel user=repository.findById(id)
                        .orElseThrow(()->new BusinessException("User not found",HttpStatus.NOT_FOUND));
        repository.deleteById(id);
        producerMessaging.sendEmailMessage(user.getEmail(),"User deleted");
    }

    @Override
    public JwtResponse login(UserInputDto dto) {
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
