package com.ua07.notifications.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    public static final String TRANSACTION_QUEUE = "transaction-queue";
    public static final String MERCHANT_QUEUE = "merchant-queue";

    @Bean
    public Queue transactionQueue() {
        return new Queue(TRANSACTION_QUEUE, true);
    }

    @Bean
    public Queue merchantQueue() {
        return new Queue(MERCHANT_QUEUE, true);
    }
}
