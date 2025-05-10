package com.ua07.transactions.command.ProcessWalletPayment;

import com.ua07.shared.command.Command;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.Wallet;
import com.ua07.transactions.repository.WalletRepository;

public class ProcessWalletPaymentCommand
        implements Command<
                ProcessWalletPaymentCommandRequest, ProcessWalletPaymentCommandResponse> {

    WalletRepository walletRepository;
    Order order;

    public ProcessWalletPaymentCommand(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public ProcessWalletPaymentCommandResponse execute(ProcessWalletPaymentCommandRequest request) {
        ProcessWalletPaymentCommandResponse response = new ProcessWalletPaymentCommandResponse();
        try {
            order = request.getOrder();

            Wallet wallet = walletRepository.findByUserId(order.getUserId());

            wallet.setAmount(wallet.getAmount() - order.getTotalAmount());
            walletRepository.save(wallet);
            response.setSuccess(true);
            response.setMessage("Payment processed successfully");
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public void undo() {
        Wallet wallet = walletRepository.findByUserId(order.getUserId());
        wallet.setAmount(wallet.getAmount() + order.getTotalAmount());
        walletRepository.save(wallet);
    }
}
