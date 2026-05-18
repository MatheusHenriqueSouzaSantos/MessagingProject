package com.example.ProducerMessagingProject;

import com.example.ProducerMessagingProject.user.UserModel;
import com.example.ProducerMessagingProject.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class StartRunner implements CommandLineRunner {

    private static final String ADMIN_USERNAME="admin";

    private static final String ADMIN_EMAIL="admin@gmail.com";

    private static final String ADMIN_PASSWORD="123456";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public StartRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.existsByEmail(ADMIN_EMAIL)){
            return;
        }
        UserModel adminUser=new UserModel(ADMIN_USERNAME,ADMIN_EMAIL, passwordEncoder.encode(ADMIN_PASSWORD));
        userRepository.save(adminUser);
    }
}
