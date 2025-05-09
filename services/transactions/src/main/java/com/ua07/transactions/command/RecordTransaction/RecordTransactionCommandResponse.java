package com.ua07.transactions.command.RecordTransaction;

import com.ua07.shared.command.CommandResponse;
import com.ua07.transactions.model.Transaction;


public class RecordTransactionCommandResponse extends CommandResponse{

    Transaction transaction;
    boolean success;
    String message;

    Transaction getTransaction() {
        return transaction;
    }

    void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public boolean isSuccess() {
        return success;
    }

    void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }
    void setMessage(String message) {
        this.message = message;
    }
    
}
