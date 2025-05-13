package com.ua07.merchants.producer;

import com.ua07.merchants.repository.ProductRepository;
import com.ua07.shared.rabbitmq.RabbitMQConstants;
import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MerchantQueueProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ProductRepository productRepository;

    public MerchantQueueProducer(RabbitTemplate rabbitTemplate,
            ProductRepository productRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.productRepository = productRepository;
    }

    public void notifyProductShortage(String productId, int remainingStock) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "SHORTAGE");
        // Ensure userId is sent as a String
        String merchantId = productRepository
                .findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"))
                .getMerchantId() != null ? productRepository.findById(productId).get().getMerchantId().toString() : null;
        message.put("userId", merchantId);
        message.put("productId", productId);
        message.put("remainingStock", remainingStock);

        rabbitTemplate.convertAndSend(RabbitMQConstants.MERCHANT_QUEUE, message);
    }
}
