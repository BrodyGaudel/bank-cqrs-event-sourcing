package com.brodygaudel.bank.common.event.account;

import com.brodygaudel.bank.common.event.BaseEvent;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Event representing the crediting of an account in the system.
 *
 * <p>
 * This event is triggered when an account is successfully credited with a certain amount. It includes the unique identifier
 * of the account, the credited amount, a description of the credit operation, and the timestamp of the last update.
 * </p>
 *
 * @see BaseEvent
 */
@Getter
public class AccountCreditedEvent extends BaseEvent<String> {

    /**
     * The amount credited to the account.
     */
    private final BigDecimal amount;

    /**
     * A description of the credit operation.
     */
    private final String description;

    /**
     * The timestamp of the last update, indicating when the credit operation occurred.
     */
    private final LocalDateTime lastUpdate;

    /**
     * Constructs a new AccountCreditedEvent with the specified details.
     *
     * @param id The unique identifier associated with the event, representing the credited account.
     * @param amount The amount credited to the account.
     * @param description A description of the credit operation.
     * @param lastUpdate The timestamp of the last update, indicating when the credit operation occurred.
     */
    public AccountCreditedEvent(String id, BigDecimal amount, String description, LocalDateTime lastUpdate) {
        super(id);
        this.amount = amount;
        this.description = description;
        this.lastUpdate = lastUpdate;
    }
}

