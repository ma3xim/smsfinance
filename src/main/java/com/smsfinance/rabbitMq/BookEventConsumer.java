package com.smsfinance.rabbitMq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BookEventConsumer {
    @RabbitListener(queues = "bookQueue")
    public void receiveMessage(String message) {
        System.out.println("Получено сообщение от RabbitMQ: " + message + System.currentTimeMillis());
        //логика обработки уведомления
    }
}