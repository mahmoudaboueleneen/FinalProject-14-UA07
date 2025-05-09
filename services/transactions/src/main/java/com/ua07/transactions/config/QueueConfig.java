package com.ua07.transactions.config;

import com.ua07.shared.rabbitmq.RabbitMQConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Bean
    public Queue transactionQueue() {
        return new Queue(RabbitMQConstants.TRANSACTION_QUEUE, true);
    }
}
