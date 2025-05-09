package com.ua07.transactions.controller;

import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.PaymentMethod;

import com.ua07.transactions.payment.PayCommand;
import com.ua07.transactions.payment.PaymentInvoker;
import com.ua07.transactions.payment.PaymentStrategy;

import com.ua07.transactions.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Map<PaymentMethod, PaymentStrategy> strategyMap;
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService, List<PaymentStrategy> strategies) {
        this.orderService = orderService;
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(PaymentStrategy::getPaymentMethod, Function.identity()));
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

    @PostMapping("/{id}/pay")
    public Object payOrder(@PathVariable UUID id, @RequestParam PaymentMethod paymentMethod) throws Exception {
        Order order = orderService.getOrderById(id);

        PaymentStrategy paymentStrategy = strategyMap.get(paymentMethod);
        if (paymentStrategy == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported payment method: " + paymentMethod);
        }

        PaymentInvoker invoker = new PaymentInvoker();
        PayCommand payCommand = new PayCommand(order, paymentStrategy);
        invoker.setCommand(payCommand);
        return invoker.executeCommand();
    }

}
