package com.brodygaudel.bank.common.command.account;

import com.brodygaudel.bank.common.command.BaseCommand;
import com.brodygaudel.bank.common.enums.AccountStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Command to suspend an account.
 *
 * <p>
 * This command is used to initiate the suspension of an account. It includes the unique identifier of the account,
 * the new status (AccountStatus.SUSPENDED), and the timestamp of the last update inherited from the BaseCommand class.
 * </p>
 *
 * @see BaseCommand
 */
@Getter
public class SuspendAccountCommand extends BaseCommand<String> {

    /**
     * The new status of the account after suspension (AccountStatus.SUSPENDED).
     */
    private final AccountStatus status;

    /**
     * The timestamp indicating when the account suspension operation occurred.
     */
    private final LocalDateTime lastUpdate;

    /**
     * Constructs a new instance of SuspendAccountCommand with the specified parameters.
     *
     * @param id         The unique identifier of the account to be suspended.
     * @param status     The new status of the account after suspension (AccountStatus.SUSPENDED).
     * @param lastUpdate The timestamp indicating when the account suspension operation occurred.
     */
    public SuspendAccountCommand(String id, AccountStatus status, LocalDateTime lastUpdate) {
        super(id);
        this.status = status;
        this.lastUpdate = lastUpdate;
    }
}

