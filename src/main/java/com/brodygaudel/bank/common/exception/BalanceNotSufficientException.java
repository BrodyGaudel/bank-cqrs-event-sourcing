package com.brodygaudel.bank.common.exception;

public class BalanceNotSufficientException extends RuntimeException{
    public BalanceNotSufficientException(String message) {
        super(message);
    }
}
