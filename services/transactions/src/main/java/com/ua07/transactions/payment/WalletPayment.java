package com.ua07.transactions.payment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.PaymentMethod;
import com.ua07.transactions.model.Wallet;
import com.ua07.transactions.repository.WalletRepository;

@Component
public class WalletPayment extends PaymentStrategy {
    @Autowired
    WalletRepository walletRepository;

    @Override
    protected void performPayment(Order order) throws Exception {
        Wallet wallet = walletRepository.findByUserId(order.getUserId());
        if (wallet == null) {
            throw new RuntimeException("Wallet not found for user: " + order.getUserId());
        }
        if (wallet.getAmount() < order.getTotalAmount()) {
            throw new RuntimeException("Insufficient funds in wallet for user: " + order.getUserId());
        }
        wallet.setAmount(wallet.getAmount() - order.getTotalAmount());
        walletRepository.save(wallet);
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.WALLET;
    }
}