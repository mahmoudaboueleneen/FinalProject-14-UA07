package com.ua07.transactions.controller;

import com.ua07.shared.command.CommandResponse;
import com.ua07.transactions.dto.OrderConfirmationResponse;
import com.ua07.transactions.dto.OrderRequest;
import com.ua07.transactions.dto.OrderResponse;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.PaymentMethod;
import com.ua07.transactions.service.InvoiceService;
import com.ua07.transactions.service.OrderService;
import com.ua07.transactions.service.PaymentService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final InvoiceService invoiceService;

    @Autowired
    public OrderController(OrderService orderService, PaymentService paymentService, InvoiceService invoiceService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest order,@RequestHeader(value = "X-User-Id", required = false) String userId) {

        return orderService.createOrder(order, userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable UUID id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        orderService.deleteOrder(id);
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<OrderConfirmationResponse> confirmOrder(@PathVariable UUID id) {
        return orderService.confirmOrder(id);
    }

    @GetMapping("/confirmed")
    public List<Order> getConfirmedOrders(
            @RequestParam String startDate, @RequestParam String endDate) {
        return orderService.getConfirmedOrders(startDate, endDate);
    }

    @PostMapping("/{orderId}/pay")
    public CommandResponse payOrder(
            @PathVariable UUID orderId, @RequestParam PaymentMethod paymentMethod)
            throws Exception {
        return paymentService.pay(orderId, paymentMethod);
    }
    @GetMapping("/{id}/invoice")
    public ResponseEntity<byte[]> generateInvoice(@PathVariable UUID id) {
        byte[] pdfBytes = invoiceService.generateInvoicePdf(id);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=invoice-" + id + ".pdf")
                .header("Content-Type", "application/pdf")
                .body(pdfBytes);
    }
}
