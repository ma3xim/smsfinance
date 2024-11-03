package com.smsfinance;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SmsfinanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsfinanceApplication.class, args);
    }

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }

    @Bean
    public Queue bookQueue() {
        return new Queue("bookQueue", false); // Создаем очередь "bookQueue"
    }

}
