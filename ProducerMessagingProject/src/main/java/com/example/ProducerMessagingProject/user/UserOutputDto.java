package com.example.ProducerMessagingProject.user;

import java.util.UUID;

public record UserOutputDto(UUID id, String userName, String email) {
}
