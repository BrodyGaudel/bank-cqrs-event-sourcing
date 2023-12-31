package com.brodygaudel.bank.common.exception;

public class NicAlreadyExistException extends RuntimeException{
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NicAlreadyExistException(String message) {
        super(message);
    }
}
