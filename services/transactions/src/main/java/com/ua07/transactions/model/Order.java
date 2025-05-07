package com.ua07.transactions.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    //no need for getters and setter for using lombok
    //other constructors are implemented by NoArgs and AllArgsConstructor

    public Order(UUID userId, Double totalAmount, OrderStatus status, LocalDateTime createdAt,
                 LocalDateTime confirmedAt, LocalDateTime cancelledAt, LocalDateTime deliveredAt,
                 String shippingAddress, List<OrderLineItem> lineItems, Transaction transaction) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.confirmedAt = confirmedAt;
        this.cancelledAt = cancelledAt;
        this.deliveredAt = deliveredAt;
        this.shippingAddress = shippingAddress;
        this.lineItems = lineItems;
        this.transaction = transaction;
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID userId;
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime cancelledAt;
    private LocalDateTime deliveredAt;

    private String shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLineItem> lineItems;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Transaction transaction;

}