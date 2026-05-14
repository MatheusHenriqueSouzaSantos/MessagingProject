package com.example.ProducerMenssagingProject.user;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
public class UserProducerMessaging {
    private final RabbitTemplate rabbitTemplate;

    public UserProducerMessaging(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmailMessage(String emailToSend,String message){
        rabbitTemplate.convertAndSend(
            UserRabbitConfig.USER_EXCHANGE_NAME,
            UserRabbitConfig.USER_SEND_EMAIL_ROUTING_KEY,
            new SendEmailDto(emailToSend,message)
        );
    }
}
