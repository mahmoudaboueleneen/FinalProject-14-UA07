package com.ua07.transactions.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    public Wallet(UUID userId, Double amount) {
        this.userId = userId;
        this.amount = amount;
    }

    //no need for getters and setter for using lombok
    //other constructors are implemented by NoArgs and AllArgsConstructor

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "uuid", nullable = false)
    private UUID userId;

    private Double amount;
}
