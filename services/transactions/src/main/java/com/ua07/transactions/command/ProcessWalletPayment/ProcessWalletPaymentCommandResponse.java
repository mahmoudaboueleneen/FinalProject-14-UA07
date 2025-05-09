package com.ua07.transactions.command.ProcessWalletPayment;

import com.ua07.shared.command.CommandResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessWalletPaymentCommandResponse extends CommandResponse {

    boolean success;
    String message;
}
