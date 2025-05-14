package com.ua07.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderConfirmationResponse {
    private String message;
    private String orderId;
    private boolean success;
}
