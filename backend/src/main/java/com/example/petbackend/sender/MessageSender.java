package com.example.petbackend.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String routingKey, Object message) {
        rabbitTemplate.convertAndSend(routingKey, message);
    }
}
