package com.example.ProducerMessagingProject.user;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRabbitConfig {
    public static final String USER_EXCHANGE_NAME="user.exchange";
    public static final String USER_SEND_EMAIL_ROUTING_KEY ="user.send.email.routing.key";

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
