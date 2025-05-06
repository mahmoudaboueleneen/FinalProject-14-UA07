package com.ua07.transactions.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItem {

    public OrderLineItem(String productId, String name, Integer count,
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private String name;
    private Integer count;
    private Double unitCost;
    private Double totalCost;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
