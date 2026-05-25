package com.example.ProducerMessagingProject.user.dto;

import com.example.ProducerMessagingProject.user.messaging.TypeMessage;

public record SendEmailDto(String emailToSend, TypeMessage typeMessage, String message) {
}
