package com.ua07.transactions.command;

import com.ua07.transactions.model.Order;
import com.ua07.transactions.strategy.PaymentStrategy;

public class PayCommand extends OrderCommand {

    private final PaymentStrategy paymentStrategy;

    public PayCommand(Order order, PaymentStrategy paymentStrategy) {
        super(order);
        this.paymentStrategy = paymentStrategy;
    }

    @Override
    public Object execute() throws Exception {
        return this.paymentStrategy.pay(this.order.getId());
    }

}
