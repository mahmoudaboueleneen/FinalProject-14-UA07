package com.ua07.transactions.command.RecordTransaction;

import com.ua07.shared.command.CommandRequest;
import com.ua07.transactions.model.Order;
import com.ua07.transactions.enums.PaymentMethod;
import com.ua07.transactions.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordTransactionCommandRequest extends CommandRequest {
    private Order order;
    private PaymentMethod paymentMethod;
    private TransactionStatus transactionStatus;
}
