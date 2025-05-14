package com.ua07.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderLineItemResponse {
    private String productId;
    private String productName;
    private int RequestedQuantity;
    private int maxQuantity;
    private double price;
    private double totalPrice;
    private String message;
    private boolean valid;
}
