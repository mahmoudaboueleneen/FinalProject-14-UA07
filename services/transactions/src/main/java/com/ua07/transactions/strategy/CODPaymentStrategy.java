package com.ua07.transactions.strategy;


import com.ua07.shared.command.CommandExecutor;
import com.ua07.transactions.command.RecordTransaction.RecordTransactionCommand;
import com.ua07.transactions.command.RecordTransaction.RecordTransactionCommandRequest;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.PaymentMethod;
import com.ua07.transactions.model.TransactionStatus;
import com.ua07.transactions.repository.OrderRepository;
import com.ua07.transactions.repository.TransactionRepository;

public class CODPaymentStrategy implements PaymentStrategy {
    CommandExecutor commandExecutor;

    OrderRepository orderRepository;

    TransactionRepository transactionRepository;

    public CODPaymentStrategy(CommandExecutor commandExecutor, OrderRepository orderRepository, TransactionRepository transactionRepository) {
        this.commandExecutor = commandExecutor;
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
    }
    
    public void pay(Order order) {
        RecordTransactionCommandRequest request = new RecordTransactionCommandRequest(order, PaymentMethod.COD, TransactionStatus.PENDING);
        RecordTransactionCommand command = new RecordTransactionCommand(this.orderRepository,this.transactionRepository);

        System.out.println("Executing COD payment strategy for order: " + order.getId());
        commandExecutor.execute(command, request);

    }


}
