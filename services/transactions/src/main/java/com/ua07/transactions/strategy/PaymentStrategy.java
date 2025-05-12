package com.ua07.transactions.strategy;

import com.ua07.shared.command.CommandResponse;
import com.ua07.transactions.model.Order;
import org.springframework.stereotype.Component;

@Component
public interface PaymentStrategy {
    CommandResponse pay(Order order);
}
