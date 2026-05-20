package com.example.ProducerMessagingProject.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank(message = "email must not be blank")
        @Email(message = "email must be a valid format")
        String email,
        @NotBlank(message = "password must not be blank")
        String password) {
}
