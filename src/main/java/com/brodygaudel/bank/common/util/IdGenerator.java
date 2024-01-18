package com.brodygaudel.bank.common.util;

/**
 * An interface for generating unique IDs based on the current date, time, and a counter.
 */
public interface IdGenerator {

    /**
     * Generates a unique ID based on the current date, time, and a counter.
     *
     * @return A unique ID.
     */
    String autoGenerate();
}
