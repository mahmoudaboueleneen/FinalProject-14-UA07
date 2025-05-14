package com.ua07.transactions.dto;

import lombok.Data;
import java.util.ArrayList;

@Data
public class OrderResponse {
    // list of order line item responses
    private ArrayList<OrderLineItemResponse> orderLineItems;
}
