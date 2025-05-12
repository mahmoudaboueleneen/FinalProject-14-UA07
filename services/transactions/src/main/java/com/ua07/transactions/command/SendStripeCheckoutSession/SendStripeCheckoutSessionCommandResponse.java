package com.ua07.transactions.command.SendStripeCheckoutSession;

import com.ua07.shared.command.CommandResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendStripeCheckoutSessionCommandResponse extends CommandResponse {

    private String checkoutUrl;
    private String message;
}
