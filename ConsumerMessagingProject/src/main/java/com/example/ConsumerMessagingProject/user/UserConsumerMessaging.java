package com.example.ConsumerMessagingProject.user;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumerMessaging {
    public static final String USER_SEND_EMAIL_QUEUE_NAME="user.send.email.queue";
    @RabbitListener(queues =USER_SEND_EMAIL_QUEUE_NAME )
    public void sendEmail(SendEmailDto dto){
        //mock email
        System.out.println("send message: "+ dto.message()+ " to address email: "+ dto.emailToSend());
    }
}
