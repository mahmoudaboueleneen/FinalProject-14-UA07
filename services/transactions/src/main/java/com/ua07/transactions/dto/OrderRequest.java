package com.ua07.transactions.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private List<OrderLineItemRequest> orderLineItems;
}
