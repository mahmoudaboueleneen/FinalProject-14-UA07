package com.ua07.transactions.command.RecordTransaction;

import com.ua07.shared.command.Command;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.Transaction;
import com.ua07.transactions.repository.OrderRepository;
import com.ua07.transactions.repository.TransactionRepository;

public class RecordTransaction implements Command<RecordTransactionRequest, RecordTransactionResponse> {

    OrderRepository orderRepository;
    
    TransactionRepository transactionRepository;

    Transaction transaction;

    public RecordTransaction(OrderRepository orderRepository, TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public RecordTransactionResponse execute(RecordTransactionRequest request) {
        RecordTransactionResponse response = new RecordTransactionResponse();
        try {
            Order order = request.getOrder();
            Transaction transaction = new Transaction(order,request.getPaymentMethod(),request.getTransactionStatus());
            this.transaction = transaction;
            order.setTransaction(transaction);
            orderRepository.save(order);
            response.setTransaction(transaction);
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return response;
}

    @Override
    public void undo() {
        if (transaction != null) {
            Order order = transaction.getOrder();
            order.setTransaction(null);
            orderRepository.save(order);
            transactionRepository.delete(transaction);
        }
    }
}
