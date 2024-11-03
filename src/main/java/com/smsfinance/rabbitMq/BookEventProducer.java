package com.smsfinance.rabbitMq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookEventProducer {
    private final AmqpTemplate amqpTemplate;

    @Autowired
    public BookEventProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendBookEvent(String message) {
        amqpTemplate.convertAndSend("bookQueue", message);
        System.out.println("Отправлено сообщения в RabbitMQ: " + message + System.currentTimeMillis());
    }
}
