package com.ua07.transactions.service;

import com.ua07.shared.command.CommandExecutor;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.PaymentMethod;
import com.ua07.transactions.repository.OrderRepository;
import com.ua07.transactions.repository.TransactionRepository;
import com.ua07.transactions.repository.WalletRepository;
import com.ua07.transactions.strategy.CODPaymentStrategy;
import com.ua07.transactions.strategy.PaymentStrategy;
import com.ua07.transactions.strategy.WalletPaymentStrategy;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    OrderRepository orderRepository;
    TransactionRepository transactionRepository;
    WalletRepository walletRepository;

    public PaymentService(
            OrderRepository orderRepository,
            TransactionRepository transactionRepository,
            WalletRepository walletRepository) {
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    public void pay(UUID orderId, PaymentMethod paymentMethod) {
        Order order =
                orderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order not found"));

        PaymentStrategy paymentStrategy;
        CommandExecutor commandExecutor = new CommandExecutor();
        switch (paymentMethod) {
            case WALLET:
                paymentStrategy =
                        new WalletPaymentStrategy(
                                commandExecutor,
                                orderRepository,
                                transactionRepository,
                                walletRepository);
                break;
            case COD:
                paymentStrategy =
                        new CODPaymentStrategy(
                                commandExecutor, orderRepository, transactionRepository);
                break;
            default:
                throw new RuntimeException("Invalid payment method: " + paymentMethod);
        }

        paymentStrategy.pay(order);
    }
}
