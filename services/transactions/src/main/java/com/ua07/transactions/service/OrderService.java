package com.ua07.transactions.service;

import com.ua07.transactions.client.StockClient;
import com.ua07.transactions.dto.OrderConfirmationResponse;
import com.ua07.transactions.dto.OrderLineItemRequest;
import com.ua07.transactions.dto.OrderRequest;
import com.ua07.transactions.dto.OrderResponse;
import com.ua07.transactions.dto.OrderLineItemResponse;
import com.ua07.transactions.enums.OrderStatus;
import com.ua07.transactions.enums.PaymentMethod;
import com.ua07.transactions.enums.TransactionStatus;
import com.ua07.transactions.model.*;
import com.ua07.transactions.producer.TransactionQueueProducer;
import com.ua07.transactions.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {

    private final TransactionQueueProducer transactionQueueProducer;
    private final OrderRepository orderRepository;
    private final StockClient stockClient;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        TransactionQueueProducer transactionQueueProducer, StockClient stockClient) {
        this.orderRepository = orderRepository;
        this.transactionQueueProducer = transactionQueueProducer;
        this.stockClient = stockClient;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Cacheable(value = "order", key = "#id")
    public Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with ID: " + id));
    }

    public double getProductPrice(String productId) {
        Map<String, Object> product = stockClient.getProductById(productId);
        return (double) product.get("price");
    }


    public OrderResponse getOrderResponse(OrderRequest orderRequest) {
        OrderResponse response = new OrderResponse();
        response.setOrderLineItems(new ArrayList<OrderLineItemResponse>());
        for (OrderLineItemRequest item : orderRequest.getOrderLineItems()) {
            OrderLineItemResponse orderLineItemResponse = new OrderLineItemResponse(
                    item.getProductId(),
                    item.getProductName(),
                    item.getQuantity(),
                    0,
                    0,
                    0, "", true);
            try {
                int stock = getStock(item.getProductId());
                double price = getProductPrice(item.getProductId());

                orderLineItemResponse.setMaxQuantity(stock);
                orderLineItemResponse.setPrice(price);
                orderLineItemResponse.setTotalPrice(price * item.getQuantity());

                if (stock >= item.getQuantity()) {
                    orderLineItemResponse.setMessage("Enough stock available");
                } else {
                    orderLineItemResponse.setMessage("Not enough stock available");
                    orderLineItemResponse.setValid(false);
                }
            } catch (Exception e) {
                System.out.println("Error fetching product details: " + e.getMessage());
                orderLineItemResponse.setMessage("Item unavailable");
                orderLineItemResponse.setValid(false);
            }
            response.getOrderLineItems().add(orderLineItemResponse);
        }
        return response;
    }

    public boolean isOrderValid(OrderResponse orderResponse) {
        for (OrderLineItemResponse item : orderResponse.getOrderLineItems()) {
            if (!item.isValid()) {
                return false;
            }
        }
        return true;
    }

    public ResponseEntity<OrderResponse> createOrder(OrderRequest order, UUID userId) {
        OrderResponse orderResponse = getOrderResponse(order);
        System.out.println("OrderResponse: " + orderResponse.getOrderLineItems());
        if (!isOrderValid(orderResponse)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(orderResponse);
        }
        Order newOrder = makeOrder(orderResponse, userId);
        orderRepository.save(newOrder);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderResponse);
    }

    @CacheEvict(value = "order", key = "#id")
    public void deleteOrder(UUID id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Order not found with ID: " + id);
        }
        orderRepository.deleteById(id);
    }

    private Order makeOrder(OrderResponse orderResponse, UUID userId) {
        System.out.println("OrderResponse to make order: " + orderResponse.getOrderLineItems());

        Order order = new Order();
        order.setLineItems(new ArrayList<>());
        order.setUserId(userId);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        for (OrderLineItemResponse item : orderResponse.getOrderLineItems()) {
            OrderLineItem orderLineItem = new OrderLineItem();
            orderLineItem.setProductId(UUID.fromString(item.getProductId()));
            orderLineItem.setName(item.getProductName());
            orderLineItem.setCount(item.getRequestedQuantity());
            orderLineItem.setUnitCost(item.getPrice());
            orderLineItem.setTotalCost(item.getTotalPrice());
            orderLineItem.setOrder(order);
            order.getLineItems().add(orderLineItem);
            stockClient.adjustStock(orderLineItem.getProductId().toString(), -(orderLineItem.getCount()));
        }

        order.setTotalAmount(orderResponse.getOrderLineItems().stream()
                .mapToDouble(OrderLineItemResponse::getTotalPrice)
                .sum());
        return order;

    }

    @CacheEvict(value = "order", key = "#orderId")
    public ResponseEntity<OrderConfirmationResponse> confirmOrder(UUID orderId) {
        // Order order = orderRepository.findById(orderId)
        //         .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " +
        // orderId));

        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new OrderConfirmationResponse("Order not found with ID: " + orderId, orderId.toString(), false));
        }
        Order order = optionalOrder.get();

        if (order.getStatus() != OrderStatus.CREATED) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new OrderConfirmationResponse("Order is not in a valid state for confirmation,", orderId.toString(), false));
        }
        if (order.getTransaction() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new OrderConfirmationResponse("Order not paid yet.", orderId.toString(), false));
        }
        if (order.getTransaction().getPaymentMethod() != PaymentMethod.COD && order.getTransaction().getStatus() != TransactionStatus.APPROVED) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new OrderConfirmationResponse("Order payment not approved yet.", orderId.toString(), false));
        }

        order.setStatus(OrderStatus.CONFIRMED);
        order.setConfirmedAt(LocalDateTime.now());
        orderRepository.save(order);
        transactionQueueProducer.notifyOrderEvent(
                order.getUserId().toString(),
                "OrderConfirmation",
                "Order confirmed with ID: " + order.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new OrderConfirmationResponse("Order confirmed.", orderId.toString(), true));
    }

    public int getStock(String productId) {
        Map<String, Object> stock = stockClient.viewStock(productId);
        return (int) stock.get("stock");
    }

    public List<Order> getConfirmedOrders(String startDate, String endDate) {
        LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate).atTime(23, 59, 59);

        return orderRepository.findByStatusAndCreatedAtBetween(OrderStatus.CONFIRMED, start, end);
    }

    @CachePut(value = "order", key = "#id")
    public Order updateOrder(UUID id, Order order) {
        Optional<Order> existingOptOrder = orderRepository.findById(id);
        if (existingOptOrder.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with ID: " + id);
        }
        return orderRepository.save(order);
    }

}
