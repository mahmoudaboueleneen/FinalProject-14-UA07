package com.ua07.transactions.model;

import com.ua07.transactions.enums.OrderStatus;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id @GeneratedValue private UUID id;

    @Column(columnDefinition = "uuid", nullable = false)
    private UUID userId;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;
    @Nullable private LocalDateTime confirmedAt;
    @Nullable private LocalDateTime cancelledAt;
    @Nullable private LocalDateTime deliveredAt;

    private String shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLineItem> lineItems;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Transaction transaction;

    public Order(
            UUID userId,
            Double totalAmount,
            OrderStatus status,
            LocalDateTime createdAt,
            LocalDateTime confirmedAt,
            LocalDateTime cancelledAt,
            LocalDateTime deliveredAt,
            String shippingAddress,
            List<OrderLineItem> lineItems,
            Transaction transaction) {
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

}
