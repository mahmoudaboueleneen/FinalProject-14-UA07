package com.ua07.transactions.service;

import com.ua07.transactions.model.*;
import com.ua07.transactions.producer.TransactionQueueProducer;
import com.ua07.transactions.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {

    private final TransactionQueueProducer transactionQueueProducer;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
            TransactionQueueProducer transactionQueueProducer) {
        this.orderRepository = orderRepository;
        this.transactionQueueProducer = transactionQueueProducer;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(UUID id) {
        return orderRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND, "Order not found with ID: " + id));
    }

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.getLineItems().forEach(item -> item.setOrder(order));
        Double totalAmount =
                order.getLineItems().stream().mapToDouble(OrderLineItem::getTotalCost).sum();
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    public void deleteOrder(UUID id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Order not found with ID: " + id);
        }
        orderRepository.deleteById(id);
    }

    public boolean confirmOrder(UUID orderId) {
        // Order order = orderRepository.findById(orderId)
        //         .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " +
        // orderId));

        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            return false;
        }

        Order order = optionalOrder.get();
        order.setStatus(OrderStatus.CONFIRMED);
        order.setConfirmedAt(LocalDateTime.now());
        orderRepository.save(order);
        transactionQueueProducer.notifyOrderEvent(
                order.getUserId().toString(),
                "OrderConfirmation",
                "Order confirmed with ID: " + order.getId());
        return true;
    }

    public List<Order> getConfirmedOrders(String startDate, String endDate) {
        LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate).atTime(23, 59, 59);

        return orderRepository.findByStatusAndCreatedAtBetween(OrderStatus.CONFIRMED, start, end);
    }
}
