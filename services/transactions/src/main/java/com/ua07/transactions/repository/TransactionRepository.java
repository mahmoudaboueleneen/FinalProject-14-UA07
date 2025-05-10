package com.ua07.transactions.repository;

import com.ua07.transactions.model.Transaction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {}
