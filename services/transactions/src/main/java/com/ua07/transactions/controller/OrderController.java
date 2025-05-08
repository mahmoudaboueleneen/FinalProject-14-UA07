package com.ua07.transactions.controller;

import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.PaymentMethod;

import com.ua07.transactions.payment.PayCommand;
import com.ua07.transactions.payment.PaymentInvoker;
import com.ua07.transactions.payment.PaymentStrategy;

import com.ua07.transactions.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private Map<PaymentMethod, PaymentStrategy> strategyMap;

    @Autowired
    private OrderService service;

    public OrderController(OrderService service,List<PaymentStrategy> strategies) {
        this.service = service;
        this.strategyMap = strategies.stream()
        .collect(Collectors.toMap(PaymentStrategy::getPaymentMethod, Function.identity()));
    }
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

    @PostMapping("/{id}/pay")
    public Object payOrder(@PathVariable UUID id, @RequestParam PaymentMethod paymentMethod) throws Exception {
        Order order = service.getOrderById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        PaymentStrategy paymentStrategy = strategyMap.get(paymentMethod);
        if (paymentStrategy == null) {
            throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }
        PaymentInvoker invoker = new PaymentInvoker();
        PayCommand payCommand = new PayCommand(order, paymentStrategy);
        invoker.setCommand(payCommand);
        return invoker.executeCommand();
    }

}
