package com.ua07.transactions.repository;

import com.ua07.transactions.model.OrderLineItem;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, UUID> {}
