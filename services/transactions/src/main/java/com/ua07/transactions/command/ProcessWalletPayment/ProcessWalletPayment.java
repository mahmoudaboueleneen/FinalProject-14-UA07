package com.ua07.transactions.command.ProcessWalletPayment;

import com.ua07.shared.command.Command;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.Wallet;
import com.ua07.transactions.repository.WalletRepository;

public class ProcessWalletPayment implements Command<ProcessWalletPaymentRequest, ProcessWalletPaymentResponse> {

    WalletRepository walletRepository;
    Order order;
    public ProcessWalletPayment(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public ProcessWalletPaymentResponse execute(ProcessWalletPaymentRequest request) {
        ProcessWalletPaymentResponse response = new ProcessWalletPaymentResponse();
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
