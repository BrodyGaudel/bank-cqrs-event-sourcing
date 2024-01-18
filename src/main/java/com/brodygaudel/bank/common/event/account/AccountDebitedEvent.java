package com.brodygaudel.bank.common.event.account;

import com.brodygaudel.bank.common.event.BaseEvent;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Event representing the debiting of an account in the system.
 *
 * <p>
 * This event is triggered when an account is successfully debited with a certain amount. It includes the unique identifier
 * of the account, the debited amount, a description of the debit operation, and the timestamp of the last update.
 * </p>
 *
 * @see BaseEvent
 */
@Getter
public class AccountDebitedEvent extends BaseEvent<String> {

    /**
     * The amount debited from the account.
     */
    private final BigDecimal amount;

    /**
     * A description of the debit operation.
     */
    private final String description;

    /**
     * The timestamp of the last update, indicating when the debit operation occurred.
     */
    private final LocalDateTime lastUpdate;

    /**
     * Constructs a new AccountDebitedEvent with the specified details.
     *
     * @param id The unique identifier associated with the event, representing the debited account.
     * @param amount The amount debited from the account.
     * @param description A description of the debit operation.
     * @param lastUpdate The timestamp of the last update, indicating when the debit operation occurred.
     */
    public AccountDebitedEvent(String id, BigDecimal amount, String description, LocalDateTime lastUpdate) {
        super(id);
        this.amount = amount;
        this.description = description;
        this.lastUpdate = lastUpdate;
    }
}

