package com.ua07.transactions.command.ProcessWalletPayment;

import com.ua07.shared.command.CommandRequest;
import com.ua07.transactions.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessWalletPaymentCommandRequest extends CommandRequest {
    private Order order;
}
