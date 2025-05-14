package com.ua07.transactions.repository;

import com.ua07.transactions.model.Order;
import com.ua07.transactions.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    // get all confirmed orders between two dates
    List<Order> findByStatusAndCreatedAtBetween(
            OrderStatus status, LocalDateTime start, LocalDateTime end);

    Optional<Order> findById(UUID id);
}
