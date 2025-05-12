package com.ua07.transactions.strategy;

import com.ua07.shared.command.CommandExecutor;
import com.ua07.shared.command.CommandResponse;
import com.ua07.transactions.command.ProcessWalletPayment.ProcessWalletPaymentCommand;
import com.ua07.transactions.command.ProcessWalletPayment.ProcessWalletPaymentCommandRequest;
import com.ua07.transactions.command.ProcessWalletPayment.ProcessWalletPaymentCommandResponse;
import com.ua07.transactions.command.RecordTransaction.RecordTransactionCommand;
import com.ua07.transactions.command.RecordTransaction.RecordTransactionCommandRequest;
import com.ua07.transactions.command.RecordTransaction.RecordTransactionCommandResponse;
import com.ua07.transactions.command.ValidateWalletTransaction.ValidateWalletTransactionCommand;
import com.ua07.transactions.command.ValidateWalletTransaction.ValidateWalletTransactionCommandRequest;
import com.ua07.transactions.command.ValidateWalletTransaction.ValidateWalletTransactionCommandResponse;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.PaymentMethod;
import com.ua07.transactions.model.TransactionStatus;
import com.ua07.transactions.repository.OrderRepository;
import com.ua07.transactions.repository.TransactionRepository;
import com.ua07.transactions.repository.WalletRepository;

public class WalletPaymentStrategy implements PaymentStrategy {

    WalletRepository walletRepository;

    CommandExecutor commandExecutor;

    OrderRepository orderRepository;

    TransactionRepository transactionRepository;

    public WalletPaymentStrategy(
            CommandExecutor commandExecutor,
            OrderRepository orderRepository,
            TransactionRepository transactionRepository,
            WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
        this.commandExecutor = commandExecutor;
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
    }

    public CommandResponse pay(Order order) {

        ValidateWalletTransactionCommand validateWalletTransaction =
                new ValidateWalletTransactionCommand(walletRepository);
        ValidateWalletTransactionCommandRequest validateWalletTransactionRequest =
                new ValidateWalletTransactionCommandRequest(order);

        ValidateWalletTransactionCommandResponse validateWalletTransactionResponse =
                commandExecutor.execute(
                        validateWalletTransaction, validateWalletTransactionRequest);

        if (!validateWalletTransactionResponse.isSuccess()) {
            throw new IllegalArgumentException(
                    "Wallet transaction validation failed: "
                            + validateWalletTransactionResponse.getMessage());
        }

        ProcessWalletPaymentCommand processWalletPayment =
                new ProcessWalletPaymentCommand(walletRepository);
        ProcessWalletPaymentCommandRequest processWalletPaymentRequest =
                new ProcessWalletPaymentCommandRequest(order);

        ProcessWalletPaymentCommandResponse processWalletPaymentResponse =
                commandExecutor.execute(processWalletPayment, processWalletPaymentRequest);
        if (!processWalletPaymentResponse.isSuccess()) {
            throw new RuntimeException(
                    "Wallet transaction processing failed: "
                            + processWalletPaymentResponse.getMessage());
        }

        RecordTransactionCommandRequest request =
                new RecordTransactionCommandRequest(
                        order, PaymentMethod.WALLET, TransactionStatus.APPROVED);
        RecordTransactionCommand command =
                new RecordTransactionCommand(this.orderRepository, this.transactionRepository);

        RecordTransactionCommandResponse recordTransactionResponse =
                commandExecutor.execute(command, request);

        if (!recordTransactionResponse.isSuccess()) {
            commandExecutor.undoLast(); // Undo the wallet payment if transaction recording fails
            throw new RuntimeException(
                    "Wallet transaction recording failed: "
                            + recordTransactionResponse.getMessage());
        }
        return recordTransactionResponse;
    }
}
