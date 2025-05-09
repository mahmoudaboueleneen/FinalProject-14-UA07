package com.ua07.transactions.command.ValidateWalletTransaction;

import com.ua07.shared.command.Command;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.Wallet;
import com.ua07.transactions.repository.WalletRepository;

public class ValidateWalletTransaction implements Command<ValidateWalletTransactionRequest, ValidateWalletTransactionResponse> {

    WalletRepository walletRepository;

    public ValidateWalletTransaction(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public ValidateWalletTransactionResponse execute(ValidateWalletTransactionRequest request) {
        ValidateWalletTransactionResponse response = new ValidateWalletTransactionResponse();
        try {
            Order order = request.getOrder();
            
            Wallet wallet = walletRepository.findByUserId(order.getUserId());
            if (wallet == null) {
                response.setSuccess(false);
                response.setMessage("Wallet not found for user: " + order.getUserId());
                return response;
            }
            if (wallet.getAmount() < order.getTotalAmount()) {
                response.setSuccess(false);
                response.setMessage("Insufficient balance in wallet");
                return response;
            }
            response.setSuccess(true);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
}

    @Override
    public void undo() {
        //Nothing to undo in this case
    }
}
