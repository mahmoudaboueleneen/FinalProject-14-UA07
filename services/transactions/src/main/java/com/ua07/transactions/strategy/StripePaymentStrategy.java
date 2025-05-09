package com.ua07.transactions.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.PaymentMethod;
import com.ua07.transactions.service.StripeService;

@Component
public class StripePaymentStrategy extends PaymentStrategy {

    @Autowired
    private StripeService stripeService;

    @Override
    protected void performPayment(Order order) throws Exception {
        stripeService.processPayment(order.getTotalAmount());
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.CARD;
    }

}