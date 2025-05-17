package com.ua07.transactions.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.ua07.shared.command.CommandExecutor;
import com.ua07.transactions.command.RecordTransaction.RecordTransactionCommand;
import com.ua07.transactions.command.RecordTransaction.RecordTransactionCommandRequest;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.enums.PaymentMethod;
import com.ua07.transactions.enums.TransactionStatus;
import com.ua07.transactions.repository.OrderRepository;
import com.ua07.transactions.repository.TransactionRepository;
import com.ua07.transactions.service.StripeService;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stripe")
public class StripeController {

    private StripeService stripeService;
    private OrderRepository orderRepository;
    private TransactionRepository transactionRepository;
    private static final Logger log = LoggerFactory.getLogger(StripeController.class);

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    public StripeController(
            StripeService stripeService,
            OrderRepository orderRepository,
            TransactionRepository transactionRepository) {
        this.stripeService = stripeService;
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<String> createCheckoutSession(@RequestBody Order order) {
        try {
            String checkoutUrl = stripeService.createCheckoutSession(order);
            return ResponseEntity.ok(checkoutUrl);
        } catch (Exception e) {
            // Log the error properly
            System.err.println("Error creating checkout session: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create checkout session");
        }
    }

    @GetMapping("/success")
    public ResponseEntity<String> checkoutSuccess(@RequestParam("sessionId") String sessionId) {
        // Handle successful checkout here
        String htmlResponse =
                "<html><body><h1>Checkout Successful</h1><p>Your checkout was successful! Session ID: "
                        + sessionId
                        + "</p></body></html>";
        return ResponseEntity.ok(htmlResponse);
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> checkoutCancel() {
        // Handle cancelled checkout here
        String htmlResponse =
                "<html><body><h1>Checkout Cancelled</h1><p>Your checkout process was cancelled.</p></body></html>";
        return ResponseEntity.ok(htmlResponse);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        log.info("▶️  [Webhook] Invoked /stripe/webhook (payloadLen={} sigHeader={})",
                payload.length(), sigHeader);

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            log.error("❌ Invalid Stripe signature", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        log.info("↪️  [Webhook] Parsed event type={}", event.getType());

        if ("checkout.session.completed".equals(event.getType())) {
            // Bypass the Optional deserializer and cast the raw object
            StripeObject raw = event.getData().getObject();
            if (raw instanceof Session) {
                Session session = (Session) raw;
                log.debug("[Webhook] Session metadata: {}", session.getMetadata());

                String orderIdStr = session.getMetadata().get("orderId");
                if (orderIdStr == null) {
                    log.warn("⚠️  No orderId metadata on Session {}", session.getId());
                } else {
                    try {
                        UUID orderId = UUID.fromString(orderIdStr);
                        log.info("🔄 Processing orderId={}", orderId);

                        Order order = orderRepository.findById(orderId)
                                .orElseThrow(() -> new RuntimeException("Order not found"));

                        new CommandExecutor().execute(
                                new RecordTransactionCommand(orderRepository, transactionRepository),
                                new RecordTransactionCommandRequest(order, PaymentMethod.CARD, TransactionStatus.APPROVED)
                        );

                        log.info("✅ Transaction recorded for orderId={}", orderId);
                    } catch (IllegalArgumentException e) {
                        log.error("❌ Invalid orderId format: {}", orderIdStr, e);
                    } catch (Exception e) {
                        log.error("❌ Error recording transaction for orderId={}", orderIdStr, e);
                    }
                }
            } else {
                log.warn("⚠️  Expected checkout.session.completed Session but got {}",
                        raw.getClass().getName());
            }
        }

        return ResponseEntity.ok("Received");
    }

}
