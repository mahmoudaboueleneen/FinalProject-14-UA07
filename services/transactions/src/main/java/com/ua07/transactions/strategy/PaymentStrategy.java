package com.ua07.transactions.strategy;

import org.springframework.stereotype.Component;

import com.ua07.transactions.model.Order;

@Component
public interface PaymentStrategy {
    void pay(Order order);
    
}