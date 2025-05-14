package com.ua07.transactions.service;

import com.ua07.shared.command.CommandExecutor;
import com.ua07.shared.command.CommandResponse;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.enums.PaymentMethod;
import com.ua07.transactions.repository.OrderRepository;
import com.ua07.transactions.repository.TransactionRepository;
import com.ua07.transactions.repository.WalletRepository;
import com.ua07.transactions.strategy.CODPaymentStrategy;
import com.ua07.transactions.strategy.PaymentStrategy;
import com.ua07.transactions.strategy.StripePaymentStrategy;
import com.ua07.transactions.strategy.WalletPaymentStrategy;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PaymentService {

    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final StripeService stripeService;

    public PaymentService(
            OrderRepository orderRepository,
            TransactionRepository transactionRepository,
            WalletRepository walletRepository,
            StripeService stripeService) {
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.stripeService = stripeService;
    }

    public CommandResponse pay(UUID orderId, PaymentMethod paymentMethod) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with ID: " + orderId));

        PaymentStrategy paymentStrategy;
        CommandExecutor commandExecutor = new CommandExecutor();

        paymentStrategy = switch (paymentMethod) {
            case WALLET -> new WalletPaymentStrategy(
                    commandExecutor,
                    orderRepository,
                    transactionRepository,
                    walletRepository);
            case COD -> new CODPaymentStrategy(
                    commandExecutor, orderRepository, transactionRepository);
            case PaymentMethod.CARD -> new StripePaymentStrategy(commandExecutor, stripeService);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid payment method: " + paymentMethod);
        };

        return paymentStrategy.pay(order);
    }

}
