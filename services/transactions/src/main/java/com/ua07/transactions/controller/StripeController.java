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

import java.util.Optional;
import java.util.UUID;
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
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            if ("checkout.session.completed".equals(event.getType())) {
                Optional<StripeObject> dataObject = event.getDataObjectDeserializer().getObject();
                if (dataObject.isPresent()) {
                    if (dataObject.get() instanceof Session) {
                        Session session = (Session) dataObject.get();
                        String orderIdStr = session.getMetadata().get("orderId");
                        if (orderIdStr != null) {
                            try {
                                UUID orderId = UUID.fromString(orderIdStr);
                                System.out.println("Processing orderId: " + orderId.toString());
                                Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
                                CommandExecutor commandExecutor = new CommandExecutor();
                                RecordTransactionCommandRequest recordTransactionCommandRequest =
                                        new RecordTransactionCommandRequest(order, PaymentMethod.CARD, TransactionStatus.APPROVED);
                                RecordTransactionCommand command =
                                        new RecordTransactionCommand(orderRepository, transactionRepository);
                                commandExecutor.execute(command, recordTransactionCommandRequest);
                                System.out.println("Transaction recorded for orderId: " + orderIdStr);
                            } catch (IllegalArgumentException e) {
                                System.err.println("Invalid orderId format: " + orderIdStr + ", error: " + e.getMessage());
                            }
                        } 
                    } 
                }
            } 
        } catch (SignatureVerificationException e) {
            System.err.println("Invalid signature: " + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid signature");
        } catch (Exception e) {
            System.err.println("Error processing webhook for event: " + e.getMessage());
        }
        return ResponseEntity.ok("Received");
    }
}
