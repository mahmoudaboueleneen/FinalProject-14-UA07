package com.ua07.transactions.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ua07.transactions.enums.PaymentMethod;
import com.ua07.transactions.enums.TransactionStatus;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue
    private UUID id;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    public Transaction(Order order, PaymentMethod paymentMethod, TransactionStatus status) {
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

}
