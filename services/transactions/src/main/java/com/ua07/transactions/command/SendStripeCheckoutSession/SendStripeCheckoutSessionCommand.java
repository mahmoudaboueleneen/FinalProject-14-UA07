package com.ua07.transactions.command.SendStripeCheckoutSession;

import com.ua07.shared.command.Command;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.service.StripeService;

public class SendStripeCheckoutSessionCommand
        implements Command<
                SendStripeCheckoutSessionCommandRequest, SendStripeCheckoutSessionCommandResponse> {
    private final StripeService stripeService;

    public SendStripeCheckoutSessionCommand(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @Override
    public SendStripeCheckoutSessionCommandResponse execute(
            SendStripeCheckoutSessionCommandRequest request) {
        Order order = request.getOrder();
        try {
            String checkoutUrl = this.stripeService.createCheckoutSession(order);
            return new SendStripeCheckoutSessionCommandResponse(
                   checkoutUrl, order.getId().toString());
        } catch (Exception e) {

            return new SendStripeCheckoutSessionCommandResponse(
                    null, "Failed to create checkout session: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'undo'");
    }
}
