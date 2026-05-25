package com.example.ConsumerMessagingProject.user;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumerMessaging {
    private EmailService emailService;

    public UserConsumerMessaging(EmailService emailService) {
        this.emailService = emailService;
    }

    public static final String USER_SEND_EMAIL_QUEUE_NAME="user.send.email.queue";
    @RabbitListener(queues =USER_SEND_EMAIL_QUEUE_NAME )
    public void sendEmail(SendEmailDto dto){
        emailService.sendEmail(dto);
    }
}
