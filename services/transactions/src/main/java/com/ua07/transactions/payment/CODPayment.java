package com.ua07.transactions.payment;


import org.springframework.stereotype.Component;

import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.PaymentMethod;

@Component
public class CODPayment extends PaymentStrategy{
    @Override
    protected void performPayment(Order order) throws Exception {
        // No action needed for COD payment
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.COD;
    }



}
