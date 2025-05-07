package com.ua07.transactions.repository;

import com.ua07.transactions.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}