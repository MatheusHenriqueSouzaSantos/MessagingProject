package com.example.ProducerMenssagingProject.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    private UserProducerMessaging producerMessaging;

    public UserServiceImpl(UserRepository repository, UserProducerMessaging producerMessaging) {
        this.repository = repository;
        this.producerMessaging = producerMessaging;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserModel> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserModel findById(UUID id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
    }

    @Override
    @Transactional
    public UserModel create(UserInputDto dto) {
        if(repository.existsByUserName(dto.userName())){
            throw new IllegalArgumentException("the provided user name already is used");
        }
        if(repository.existsByEmail(dto.email())){
            throw new IllegalArgumentException("the provided email already is used");
        }
        UserModel user=repository.save(new UserModel(dto.userName(), dto.email(), dto.password()));
        producerMessaging.sendEmailMessage(dto.email(),"User created");
        return user;
    }

    @Override
    @Transactional
    public UserModel update(UUID id, UserInputDto dto) {
        UserModel userFromDataBase=repository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found"));

        if(repository.existsByUserNameAndIdNot(dto.userName(),id)){
            throw new IllegalArgumentException("the provided email already is used");
        }

        if(repository.existsByEmailAndIdNot(dto.email(),id)){
            throw new IllegalArgumentException("the provided email already is used");
        }
        userFromDataBase.setUserName(dto.userName());
        userFromDataBase.setEmail(dto.email());
        userFromDataBase.setPassword(dto.password());
        producerMessaging.sendEmailMessage(dto.email(),"User updated");
        return userFromDataBase;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }


}
