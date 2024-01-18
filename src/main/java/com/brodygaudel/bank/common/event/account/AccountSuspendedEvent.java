package com.brodygaudel.bank.common.event.account;

import com.brodygaudel.bank.common.event.BaseEvent;
import com.brodygaudel.bank.common.enums.AccountStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Event representing the suspension of an account in the system.
 *
 * <p>
 * This event is triggered when an account is suspended, changing its status to indicate that it is temporarily inactive.
 * It includes the unique identifier of the account, the new status after suspension, and the timestamp of the last update.
 * </p>
 *
 * @see BaseEvent
 */
@Getter
public class AccountSuspendedEvent extends BaseEvent<String> {

    /**
     * The new status of the account after suspension.
     */
    private final AccountStatus status;

    /**
     * The timestamp of the last update, indicating when the account suspension occurred.
     */
    private final LocalDateTime lastUpdate;

    /**
     * Constructs a new AccountSuspendedEvent with the specified details.
     *
     * @param id The unique identifier associated with the event, representing the suspended account.
     * @param status The new status of the account after suspension.
     * @param lastUpdate The timestamp of the last update, indicating when the account suspension occurred.
     */
    public AccountSuspendedEvent(String id, AccountStatus status, LocalDateTime lastUpdate) {
        super(id);
        this.status = status;
        this.lastUpdate = lastUpdate;
    }
}

