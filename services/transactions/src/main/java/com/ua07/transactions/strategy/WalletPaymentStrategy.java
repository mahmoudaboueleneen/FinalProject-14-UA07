package com.ua07.transactions.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.PaymentMethod;
import com.ua07.transactions.model.Wallet;
import com.ua07.transactions.repository.WalletRepository;
import org.springframework.web.server.ResponseStatusException;

@Component
public class WalletPaymentStrategy extends PaymentStrategy {

    @Autowired
    WalletRepository walletRepository;

    @Override
    protected void performPayment(Order order) throws Exception {
        Wallet wallet = walletRepository.findByUserId(order.getUserId());

        if (wallet == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wallet not found for user: " + order.getUserId());
        }

        if (wallet.getAmount() < order.getTotalAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds in wallet for user: " + order.getUserId());
        }

        wallet.setAmount(wallet.getAmount() - order.getTotalAmount());
        walletRepository.save(wallet);
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.WALLET;
    }

}