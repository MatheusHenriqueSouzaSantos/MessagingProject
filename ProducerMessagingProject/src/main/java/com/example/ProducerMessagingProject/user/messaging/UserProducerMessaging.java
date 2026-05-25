package com.example.ProducerMessagingProject.user.messaging;

import com.example.ProducerMessagingProject.rabbitmq.RabbitConfiguration;
import com.example.ProducerMessagingProject.user.dto.SendEmailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
public class UserProducerMessaging {
    private final RabbitTemplate rabbitTemplate;

    public UserProducerMessaging(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmailMessage(SendEmailDto dto){
        rabbitTemplate.convertAndSend(
            RabbitConfiguration.USER_EXCHANGE_NAME,
            RabbitConfiguration.USER_SEND_EMAIL_ROUTING_KEY,
            dto
        );
    }
}
