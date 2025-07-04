package com.ua07.transactions.service;

import com.ua07.transactions.model.Wallet;
import com.ua07.transactions.repository.WalletRepository;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Cacheable(value = "wallet", key = "#userId")
    public double getWalletBalance(UUID userId) {
        return walletRepository.findByUserId(userId).getAmount();
    }

    public Wallet createWallet(UUID userId) {
        if (walletRepository.findByUserId(userId) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wallet already exists for user: " + userId);
        }

        return walletRepository.save(new Wallet(userId, 0.0));
    }

    @CacheEvict(value = "wallet", key = "#userId")
    public void addMoneyToWallet(UUID userId, double amount) {
        Wallet wallet = walletRepository.findByUserId(userId);

        if (wallet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found for user: " + userId);
        }

        wallet.setAmount(wallet.getAmount() + amount);
        walletRepository.save(wallet);
    }
}
