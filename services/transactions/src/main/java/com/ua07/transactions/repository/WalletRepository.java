package com.ua07.transactions.repository;

import com.ua07.transactions.model.Wallet;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Wallet findByUserId(UUID userId);
}
