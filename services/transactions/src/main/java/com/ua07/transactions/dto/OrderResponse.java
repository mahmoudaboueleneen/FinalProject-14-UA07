package com.ua07.transactions.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderResponse {
    private List<OrderLineItemResponse> orderLineItems;
}
