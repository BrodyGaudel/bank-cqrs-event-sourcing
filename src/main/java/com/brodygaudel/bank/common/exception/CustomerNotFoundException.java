package com.brodygaudel.bank.common.exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
