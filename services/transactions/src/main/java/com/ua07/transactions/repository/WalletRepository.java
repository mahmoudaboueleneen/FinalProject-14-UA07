package com.ua07.transactions.repository;

import com.ua07.transactions.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByUserId(UUID userId);
}
