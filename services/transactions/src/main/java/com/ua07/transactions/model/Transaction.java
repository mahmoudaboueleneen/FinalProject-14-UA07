package com.ua07.transactions.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    // no need for getters and setter for using lombok
    // other constructors are implemented by NoArgs and AllArgsConstructor

    public Transaction(Order order, PaymentMethod paymentMethod, TransactionStatus status) {
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    @Id @GeneratedValue private UUID id;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
