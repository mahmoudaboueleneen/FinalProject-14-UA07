package com.ua07.transactions.payment;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.PaymentMethod;
import com.ua07.transactions.model.Transaction;
import com.ua07.transactions.model.TransactionStatus;
import com.ua07.transactions.repository.OrderRepository;
import com.ua07.transactions.repository.TransactionRepository;

@Component
public abstract class PaymentStrategy {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TransactionRepository transactionRepository;

    public final Order pay(UUID orderId) throws Exception {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        try {
            performPayment(order);
            Transaction transaction = new Transaction(order, getPaymentMethod(), TransactionStatus.APPROVED);
            order.setTransaction(transaction);
            return orderRepository.save(order);
    
        } catch (Exception e) {
            Transaction transaction = new Transaction(order, getPaymentMethod(), TransactionStatus.REJECTED);
            //order.setStatus(OrderStatus.CANCELLED);
            //orderRepository.save(order);
            transactionRepository.save(transaction);
            throw e;
        }
    }

    protected abstract void performPayment(Order order) throws Exception;
    public abstract PaymentMethod getPaymentMethod();
}