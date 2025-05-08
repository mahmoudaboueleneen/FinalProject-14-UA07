package com.ua07.search.config;

import com.ua07.shared.rabbitmq.RabbitMQConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Bean
    public Queue searchQueue() {
        return new Queue(RabbitMQConstants.SEARCH_QUEUE, true);
    }

}
