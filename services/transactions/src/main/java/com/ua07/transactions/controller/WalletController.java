package com.ua07.transactions.controller;

import com.ua07.transactions.model.Wallet;
import com.ua07.transactions.service.WalletService;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public Wallet createWallet(@RequestParam UUID userId) {
        return walletService.createWallet(userId);
    }

    @PostMapping("/addFunds")
    public void addFunds(@RequestParam UUID userId, @RequestParam double amount) {
        walletService.addMoneyToWallet(userId, amount);
    }

    @GetMapping
    public double getBalance(UUID userId) {
        return walletService.getWalletBalance(userId);
    }
}
