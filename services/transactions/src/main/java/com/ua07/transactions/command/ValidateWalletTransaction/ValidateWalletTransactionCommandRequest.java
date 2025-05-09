package com.ua07.transactions.command.ValidateWalletTransaction;


import com.ua07.shared.command.CommandRequest;
import com.ua07.transactions.model.Order;

public class ValidateWalletTransactionCommandRequest extends CommandRequest{
    Order order;

    public ValidateWalletTransactionCommandRequest(Order order) {
        this.order = order;
    }
    
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    



}
