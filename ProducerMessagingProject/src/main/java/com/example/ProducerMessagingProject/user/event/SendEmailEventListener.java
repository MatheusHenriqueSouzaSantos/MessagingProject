package com.example.ProducerMessagingProject.user.event;

import com.example.ProducerMessagingProject.user.messaging.UserProducerMessaging;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class SendEmailEventListener {
    private UserProducerMessaging producerMessaging;

    public SendEmailEventListener(UserProducerMessaging producerMessaging) {
        this.producerMessaging = producerMessaging;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(SendEmailEvent event){
        producerMessaging.sendEmailMessage(event.dto());
    }
}
