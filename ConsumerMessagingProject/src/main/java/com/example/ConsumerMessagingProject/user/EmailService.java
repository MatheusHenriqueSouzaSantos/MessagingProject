package com.example.ConsumerMessagingProject.user;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(SendEmailDto dto){
        SimpleMailMessage message=new SimpleMailMessage();

        message.setFrom("noreply@messagingproject.com");

        message.setTo(dto.emailToSend());

        message.setSubject(dto.typeMessage().name());

        message.setText(dto.message());

        mailSender.send(message);

    }
}
