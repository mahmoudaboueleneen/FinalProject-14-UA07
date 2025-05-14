package com.ua07.transactions.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    private Double amount;

    public Wallet(UUID userId, Double amount) {
        this.userId = userId;
        this.amount = amount;
    }

}
