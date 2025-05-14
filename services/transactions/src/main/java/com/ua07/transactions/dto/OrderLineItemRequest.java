package com.ua07.transactions.dto;

import lombok.Data;

@Data
public class OrderLineItemRequest {
    private String productId;
    private String productName;
    private int quantity;
}
