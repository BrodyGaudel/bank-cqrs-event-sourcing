package com.brodygaudel.bank.common.command.account;

import com.brodygaudel.bank.common.command.BaseCommand;
import com.brodygaudel.bank.common.enums.AccountStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Command to create a new account.
 *
 * <p>
 * This command is used to initiate the creation of a new account. It includes the unique identifier,
 * initial balance, desired account status, creation timestamp, and the customer ID inherited from the BaseCommand class.
 * </p>
 *
 * @see BaseCommand
 */
@Getter
public class CreateAccountCommand extends BaseCommand<String> {

    /**
     * The initial balance of the new account.
     */
    private final BigDecimal balance;

    /**
     * The status to set for the new account.
     */
    private final AccountStatus status;

    /**
     * The timestamp indicating when the account is created.
     */
    private final LocalDateTime creation;

    /**
     * The unique identifier of the customer associated with the new account.
     */
    private final String customerId;

    /**
     * Constructs a new instance of CreateAccountCommand with the specified parameters.
     *
     * @param id          The unique identifier for the new account.
     * @param balance     The initial balance of the new account.
     * @param status      The status to set for the new account.
     * @param creation    The timestamp indicating when the account is created.
     * @param customerId  The unique identifier of the customer associated with the new account.
     */
    public CreateAccountCommand(String id, BigDecimal balance, AccountStatus status, LocalDateTime creation, String customerId) {
        super(id);
        this.balance = balance;
        this.status = status;
        this.creation = creation;
        this.customerId = customerId;
    }
}

