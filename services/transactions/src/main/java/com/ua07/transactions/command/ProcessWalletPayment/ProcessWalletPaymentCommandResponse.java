package com.ua07.transactions.command.ProcessWalletPayment;

import com.ua07.shared.command.CommandResponse;


public class ProcessWalletPaymentCommandResponse extends CommandResponse{


    boolean success;
    String message;


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
