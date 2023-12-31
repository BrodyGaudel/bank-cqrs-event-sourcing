package com.brodygaudel.bank.common.exception;

public class CustomerAlreadyHaveAccountException extends RuntimeException{
    public CustomerAlreadyHaveAccountException(String message) {
        super(message);
    }
}
