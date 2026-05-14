package com.example.ProducerMenssagingProject.user;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserModel> findAll();
    UserModel findById(UUID id);
    UserModel create(UserInputDto dto);
    UserModel update(UUID id, UserInputDto dto);
    void delete(UUID id);

}
