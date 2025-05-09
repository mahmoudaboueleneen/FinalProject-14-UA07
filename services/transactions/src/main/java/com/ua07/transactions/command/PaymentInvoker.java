package com.ua07.transactions.command;

public class PaymentInvoker {

    private OrderCommand orderCommand;

    public void setCommand(OrderCommand orderCommand) {
        this.orderCommand = orderCommand;
    }

    public Object executeCommand() throws Exception {
        return orderCommand.execute();
    }

}
