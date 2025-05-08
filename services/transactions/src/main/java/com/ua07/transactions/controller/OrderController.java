package com.ua07.transactions.controller;

import com.ua07.transactions.model.Order;
import com.ua07.transactions.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/create")
    public Order createOrder(@RequestBody Order order) {
        System.out.println("Creating order: " + order);
        return service.createOrder(order);
    }

    @PostMapping("/{id}/confirm")
    public boolean confirmOrder(@PathVariable UUID id) {
        return service.confirmOrder(id);
    }

    @GetMapping
    public List<Order> getAll() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public Optional<Order> getById(@PathVariable UUID id) {
        return service.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.deleteOrder(id);
    }

    @GetMapping("/confirmed")
    public List<Order> getConfirmedOrders(@RequestParam String startDate, @RequestParam String endDate) {
        return service.getConfirmedOrders(startDate, endDate);
    }
}
