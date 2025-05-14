package com.ua07.transactions.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.ua07.transactions.model.Order;
import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${stripe.checkout.success-url}")
    private String successUrl;

    @Value("${stripe.checkout.cancel-url}")
    private String cancelUrl;

    @PostConstruct
    public void initializeStripe() {
        com.stripe.Stripe.apiKey = stripeSecretKey;
    }

    public StripeService() {
        com.stripe.Stripe.apiKey = stripeSecretKey;
    }

    public String createCheckoutSession(Order order) throws StripeException {
        List<SessionCreateParams.LineItem> lineItems =
                order.getLineItems().stream()
                        .map(
                                item ->
                                        SessionCreateParams.LineItem.builder()
                                                .setPriceData(
                                                        SessionCreateParams.LineItem.PriceData
                                                                .builder()
                                                                .setCurrency(
                                                                        "usd") // Adjust currency as
                                                                // needed
                                                                .setUnitAmount(
                                                                        (long)
                                                                                (item.getUnitCost()
                                                                                        * 100)) // Amount in cents
                                                                .setProductData(
                                                                        SessionCreateParams.LineItem
                                                                                .PriceData
                                                                                .ProductData
                                                                                .builder()
                                                                                .setName(
                                                                                        item
                                                                                                .getName())
                                                                                .build())
                                                                .build())
                                                .setQuantity(item.getCount().longValue())
                                                .build())
                        .collect(Collectors.toList());

        Map<String, String> metadata = new HashMap<>();
        metadata.put("orderId", order.getId().toString());

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(
                                successUrl
                                        + "?sessionId={CHECKOUT_SESSION_ID}") // Add session ID for
                        // retrieval
                        .setCancelUrl(cancelUrl)
                        .addAllLineItem(lineItems) // Add the entire list of line items
                        .putAllMetadata(metadata)
                        .build();

        Session session = Session.create(params);
        return session.getUrl();
    }
}
