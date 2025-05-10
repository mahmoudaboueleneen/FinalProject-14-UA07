package com.ua07.transactions.command.RecordTransaction;

import com.ua07.shared.command.CommandResponse;
import com.ua07.transactions.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordTransactionCommandResponse extends CommandResponse {

    Transaction transaction;
    boolean success;
    String message;
}
