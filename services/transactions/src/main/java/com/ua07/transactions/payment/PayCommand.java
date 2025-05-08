package com.ua07.transactions.payment;


import com.ua07.transactions.model.Order;

public class PayCommand extends OrderCommand {
    private PaymentStrategy paymentStrategy;

    public PayCommand(Order order, PaymentStrategy paymentStrategy) {
        super(order);
        this.paymentStrategy = paymentStrategy;
    }

    @Override
    public Object execute() throws Exception {
        return this.paymentStrategy.pay(this.order.getId());
    }


}
