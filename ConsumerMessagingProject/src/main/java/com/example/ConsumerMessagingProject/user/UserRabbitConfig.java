package com.example.ConsumerMessagingProject.user;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRabbitConfig {
    public static final String USER_SEND_EMAIL_QUEUE_NAME="user.send.email.queue";
    public static final String USER_EXCHANGE_NAME="user.exchange";
    public static final String USER_SEND_EMAIL_ROUTING_KEY ="user.send.email.routing.key";

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue sendEmailUserQueue(){
        return new Queue(USER_SEND_EMAIL_QUEUE_NAME,true);
    }

    @Bean
    public TopicExchange userExchange(){
        return new TopicExchange(USER_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingUserCreatedQueueToUserExchange(Queue sendEmailUserQueue, TopicExchange userExchange){
        return BindingBuilder
                .bind(sendEmailUserQueue)
                .to(userExchange)
                .with(USER_SEND_EMAIL_ROUTING_KEY);
    }
}

