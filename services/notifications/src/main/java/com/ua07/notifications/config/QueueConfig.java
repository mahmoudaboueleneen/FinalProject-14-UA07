package com.ua07.notifications.config;

import com.ua07.shared.rabbitmq.RabbitMQConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class QueueConfig {

    @Bean
    public Queue transactionQueue() {
        return new Queue(RabbitMQConstants.TRANSACTION_QUEUE, true);
    }

    @Bean
    public Queue merchantQueue() {
        return new Queue(RabbitMQConstants.MERCHANT_QUEUE, true);
    }

    @Bean
public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
}

}
