package com.ua07.transactions.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItem {

    public OrderLineItem(UUID productId, String name, Integer count,
                         Double unitCost, Double totalCost, Order order) {
        this.productId = productId;
        this.name = name;
        this.count = count;
        this.unitCost = unitCost;
        this.totalCost = totalCost;
        this.order = order;
    }

//no need for getters and setter for using lombok
    //other constructors are implemented by NoArgs and AllArgsConstructor

    @Id
    @GeneratedValue
    private UUID id;

    private UUID productId;
    private String name;
    private Integer count;
    private Double unitCost;
    private Double totalCost;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
