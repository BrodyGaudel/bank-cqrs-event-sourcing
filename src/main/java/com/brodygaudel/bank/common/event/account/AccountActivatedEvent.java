package com.brodygaudel.bank.common.event.account;

import com.brodygaudel.bank.common.event.BaseEvent;
import com.brodygaudel.bank.common.enums.AccountStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Event representing the activation of an account in the system.
 *
 * <p>
 * This event is triggered when an account is successfully activated. It includes the unique identifier of the account,
 * the updated status, and the timestamp of the last update.
 * </p>
 *
 * @see BaseEvent
 */
@Getter
public class AccountActivatedEvent extends BaseEvent<String> {

    /**
     * The status of the account after activation.
     */
    private final AccountStatus status;

    /**
     * The timestamp of the last update, indicating when the account was activated.
     */
    private final LocalDateTime lastUpdate;

    /**
     * Constructs a new AccountActivatedEvent with the specified details.
     *
     * @param id The unique identifier associated with the event, representing the activated account.
     * @param status The updated status of the account after activation.
     * @param lastUpdate The timestamp of the last update, indicating when the account was activated.
     */
    public AccountActivatedEvent(String id, AccountStatus status, LocalDateTime lastUpdate) {
        super(id);
        this.status = status;
        this.lastUpdate = lastUpdate;
    }
}

