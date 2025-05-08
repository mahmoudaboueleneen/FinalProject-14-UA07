package com.ua07.transactions.service;

import com.ua07.transactions.model.*;
import com.ua07.transactions.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.getLineItems().forEach(item -> item.setOrder(order));
        Double totalAmount = order.getLineItems().stream()
                .mapToDouble(OrderLineItem::getTotalCost)
                .sum();
        order.setTotalAmount(totalAmount);
        return orderRepo.save(order);
    }

    public boolean confirmOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepo.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(OrderStatus.CONFIRMED);
            order.setConfirmedAt(LocalDateTime.now());
            orderRepo.save(order);
            return true;
        }
        return false;
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepo.findById(id);
    }

    public void deleteOrder(Long id) {
        orderRepo.deleteById(id);
    }

    public List<Order> getConfirmedOrders(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return orderRepo.findByStatusAndCreatedAtBetween(OrderStatus.CONFIRMED, start, end);
    }
}
