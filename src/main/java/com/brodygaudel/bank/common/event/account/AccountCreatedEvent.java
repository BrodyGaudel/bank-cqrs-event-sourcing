package com.brodygaudel.bank.common.event.account;

import com.brodygaudel.bank.common.event.BaseEvent;
import com.brodygaudel.bank.common.enums.AccountStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Event representing the creation of an account in the system.
 *
 * <p>
 * This event is triggered when a new account is successfully created. It includes the unique identifier of the account,
 * the initial balance, the status, the timestamp of creation, and the associated customer's identifier.
 * </p>
 *
 * @see BaseEvent
 */
@Getter
public class AccountCreatedEvent extends BaseEvent<String> {

    /**
     * The initial balance of the newly created account.
     */
    private final BigDecimal balance;

    /**
     * The status of the account after creation.
     */
    private final AccountStatus status;

    /**
     * The timestamp of the account creation.
     */
    private final LocalDateTime creation;

    /**
     * The unique identifier of the customer associated with the newly created account.
     */
    private final String customerId;

    /**
     * Constructs a new AccountCreatedEvent with the specified details.
     *
     * @param id The unique identifier associated with the event, representing the newly created account.
     * @param balance The initial balance of the account.
     * @param status The status of the account after creation.
     * @param creation The timestamp of the account creation.
     * @param customerId The unique identifier of the associated customer.
     */
    public AccountCreatedEvent(String id, BigDecimal balance, AccountStatus status, LocalDateTime creation, String customerId) {
        super(id);
        this.balance = balance;
        this.status = status;
        this.creation = creation;
        this.customerId = customerId;
    }
}

