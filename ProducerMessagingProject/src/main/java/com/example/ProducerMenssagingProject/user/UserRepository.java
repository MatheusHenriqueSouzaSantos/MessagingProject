package com.example.ProducerMenssagingProject.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
     boolean existsByUserName(String userName);
     boolean existsByEmail(String email);
     boolean existsByUserNameAndIdNot(String userName,UUID id);
     boolean existsByEmailAndIdNot(String email,UUID id);
}
