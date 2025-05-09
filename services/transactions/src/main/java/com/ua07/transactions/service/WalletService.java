package com.ua07.transactions.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ua07.transactions.repository.WalletRepository;

import com.ua07.transactions.model.Wallet;

@Service
public class WalletService {
    WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    // Method to get the wallet balance for a user
    public double getWalletBalance(UUID userId) {
        return walletRepository.findByUserId(userId).getAmount();
    }

    // create new wallet for user given userId
    public Wallet createWallet(UUID userId) {
        if (walletRepository.findByUserId(userId) != null) {
            throw new RuntimeException("Wallet already exists for user: " + userId);
        }
        return walletRepository.save(new Wallet(userId, 0.0));
    }

    // Method to add money to the wallet
    public void addMoneyToWallet(UUID userId, double amount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            throw new RuntimeException("Wallet not found for user: " + userId);
        }
        wallet.setAmount(wallet.getAmount() + amount);
        walletRepository.save(wallet);
    }
    
}
