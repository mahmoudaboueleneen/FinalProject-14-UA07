package com.ua07.transactions.command.RecordTransaction;


import com.ua07.shared.command.CommandRequest;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.PaymentMethod;
import com.ua07.transactions.model.TransactionStatus;

public class RecordTransactionRequest extends CommandRequest{
    Order order;
    PaymentMethod paymentMethod;
    TransactionStatus transactionStatus;

    public RecordTransactionRequest(Order order, PaymentMethod paymentMethod,TransactionStatus transactionStatus) {
        this.paymentMethod = paymentMethod;
        this.order = order;
        this.transactionStatus = transactionStatus;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }
    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
