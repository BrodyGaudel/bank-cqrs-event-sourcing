package com.brodygaudel.bank.common.command.account;

import com.brodygaudel.bank.common.command.BaseCommand;
import com.brodygaudel.bank.common.enums.AccountStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Command to activate an account.
 *
 * <p>
 * This command is used to initiate the activation of an account. It includes the unique identifier,
 * the desired account status, and the timestamp of the last update inherited from the BaseCommand class.
 * </p>
 *
 * @see BaseCommand
 */
@Getter
public class ActiveAccountCommand extends BaseCommand<String> {

    /**
     * The status to set the account to after activation.
     */
    private final AccountStatus status;

    /**
     * The timestamp of the last update associated with the activation.
     */
    private final LocalDateTime lastUpdate;

    /**
     * Constructs a new instance of ActiveAccountCommand with the specified parameters.
     *
     * @param id          The unique identifier for the account to be activated.
     * @param status      The status to set the account to after activation.
     * @param lastUpdate  The timestamp of the last update associated with the activation.
     */
    public ActiveAccountCommand(String id, AccountStatus status, LocalDateTime lastUpdate) {
        super(id);
        this.status = status;
        this.lastUpdate = lastUpdate;
    }
}

