package com.ua07.transactions.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ua07.shared.command.CommandExecutor;
import com.ua07.transactions.command.ProcessWalletPayment.ProcessWalletPayment;
import com.ua07.transactions.command.ProcessWalletPayment.ProcessWalletPaymentRequest;
import com.ua07.transactions.command.ProcessWalletPayment.ProcessWalletPaymentResponse;
import com.ua07.transactions.command.RecordTransaction.RecordTransaction;
import com.ua07.transactions.command.RecordTransaction.RecordTransactionRequest;
import com.ua07.transactions.command.RecordTransaction.RecordTransactionResponse;
import com.ua07.transactions.command.ValidateWalletTransaction.ValidateWalletTransaction;
import com.ua07.transactions.command.ValidateWalletTransaction.ValidateWalletTransactionRequest;
import com.ua07.transactions.command.ValidateWalletTransaction.ValidateWalletTransactionResponse;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.PaymentMethod;
import com.ua07.transactions.model.TransactionStatus;
import com.ua07.transactions.repository.OrderRepository;
import com.ua07.transactions.repository.TransactionRepository;
import com.ua07.transactions.repository.WalletRepository;

public class WalletPaymentStrategy implements PaymentStrategy {

    @Autowired
    WalletRepository walletRepository;

    CommandExecutor commandExecutor;

    OrderRepository orderRepository;

    TransactionRepository transactionRepository;

    public WalletPaymentStrategy(CommandExecutor commandExecutor, OrderRepository orderRepository, TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
        this.commandExecutor = commandExecutor;
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
    }
    
    public void pay(Order order) {

        ValidateWalletTransaction validateWalletTransaction = new ValidateWalletTransaction(walletRepository);
        ValidateWalletTransactionRequest validateWalletTransactionRequest = new ValidateWalletTransactionRequest(order);

        ValidateWalletTransactionResponse validateWalletTransactionResponse = commandExecutor.execute(validateWalletTransaction, validateWalletTransactionRequest);

        if(!validateWalletTransactionResponse.isSuccess()){
            throw new RuntimeException("Wallet transaction failed: " + validateWalletTransactionResponse.getMessage());
        }

        ProcessWalletPayment processWalletPayment = new ProcessWalletPayment(walletRepository);
        ProcessWalletPaymentRequest processWalletPaymentRequest = new ProcessWalletPaymentRequest(order);

        ProcessWalletPaymentResponse processWalletPaymentResponse =commandExecutor.execute(processWalletPayment, processWalletPaymentRequest);
        if(!processWalletPaymentResponse.isSuccess()){
            throw new RuntimeException("Wallet transaction processing failed: " + processWalletPaymentResponse.getMessage());
        }

        RecordTransactionRequest request = new RecordTransactionRequest(order, PaymentMethod.WALLET, TransactionStatus.APPROVED);
        RecordTransaction command = new RecordTransaction(this.orderRepository,this.transactionRepository);

        RecordTransactionResponse recordTransactionResponse = commandExecutor.execute(command, request);

        if(!recordTransactionResponse.isSuccess()){
            commandExecutor.undoLast(); // Undo the wallet payment if transaction recording fails
            throw new RuntimeException("Wallet transaction recording failed: " + recordTransactionResponse.getMessage());
        }

        

    }

}