package com.ua07.transactions.payment;

import com.ua07.transactions.model.Order;

public class GenerateInvoiceCommand extends OrderCommand{
    public GenerateInvoiceCommand(Order order) {
        super(order);
    }

    @Override
    public Object execute() {
        //call generate invoice method from order
        return null; // return the pdf of the invoice
    }

}
