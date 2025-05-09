package com.ua07.transactions.controller;

import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.PaymentMethod;



import com.ua07.transactions.service.OrderService;
import com.ua07.transactions.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @Autowired
    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;

    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // TODO: This should take a DTO instead.. and take the User ID from the AuthConstants.USER_ID_HEADER header
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    // TODO: Add update!!!

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        orderService.deleteOrder(id);
    }

    @PostMapping("/{id}/confirm")
    public boolean confirmOrder(@PathVariable UUID id) {
        return orderService.confirmOrder(id);
    }
    
    @GetMapping("/confirmed")
    public List<Order> getConfirmedOrders(@RequestParam String startDate, @RequestParam String endDate) {
        return orderService.getConfirmedOrders(startDate, endDate);
    }

    @PostMapping("/{orderId}/pay")
    public void payOrder(@PathVariable UUID orderId, @RequestParam PaymentMethod paymentMethod) throws Exception {
        paymentService.pay(orderId, paymentMethod);
    }

}
