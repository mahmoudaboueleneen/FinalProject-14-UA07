package com.ua07.transactions.controller;

import com.ua07.transactions.model.Wallet;
import com.ua07.transactions.service.WalletService;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    // TODO: Take userId from header? idk
    @PostMapping
    public Wallet createWallet(@RequestParam UUID userId) {
        return walletService.createWallet(userId);
    }

    // TODO: Take userId from header? idk
    @PostMapping("/addFunds")
    public void addFunds(@RequestParam UUID userId, @RequestParam double amount) {
        walletService.addMoneyToWallet(userId, amount);
    }

    // TODO: Take userId from header? idk
    @GetMapping
    public double getBalance(UUID userId) {
        return walletService.getWalletBalance(userId);
    }

}

