package com.ua07.transactions.command;

import com.ua07.transactions.model.Order;

public abstract class OrderCommand {
    Order order;

    public OrderCommand(Order order) {
        this.order = order;
    }

    abstract Object execute() throws Exception;
}
