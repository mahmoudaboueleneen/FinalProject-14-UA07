package com.ua07.transactions.strategy;

import com.ua07.shared.command.CommandExecutor;
import com.ua07.shared.command.CommandResponse;
import com.ua07.transactions.command.SendStripeCheckoutSession.SendStripeCheckoutSessionCommand;
import com.ua07.transactions.command.SendStripeCheckoutSession.SendStripeCheckoutSessionCommandRequest;
import com.ua07.transactions.command.SendStripeCheckoutSession.SendStripeCheckoutSessionCommandResponse;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.service.StripeService;

public class StripePaymentStrategy implements PaymentStrategy {
    CommandExecutor commandExecutor;
    StripeService stripeService;

    public StripePaymentStrategy(CommandExecutor commandExecutor, StripeService stripeService) {
        this.commandExecutor = commandExecutor;
        this.stripeService = stripeService;
    }

    @Override
    public CommandResponse pay(Order order) {
        SendStripeCheckoutSessionCommandRequest request =
                new SendStripeCheckoutSessionCommandRequest(order);
        SendStripeCheckoutSessionCommand command =
                new SendStripeCheckoutSessionCommand(stripeService);
        SendStripeCheckoutSessionCommandResponse response =
                commandExecutor.execute(command, request);
        return response;
    }
}
