package com.ua07.transactions.command.ProcessWalletPayment;


import com.ua07.shared.command.CommandRequest;
import com.ua07.transactions.model.Order;

public class ProcessWalletPaymentRequest extends CommandRequest{
    Order order;
    
    public ProcessWalletPaymentRequest(Order order) {
        this.order = order;
    }
    
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    

}
