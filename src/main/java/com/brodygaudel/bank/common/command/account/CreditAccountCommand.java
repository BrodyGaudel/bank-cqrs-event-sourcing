package com.brodygaudel.bank.common.command.account;

import com.brodygaudel.bank.common.command.BaseCommand;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Command to credit an amount to an account.
 *
 * <p>
 * This command is used to initiate a credit operation on an account. It includes the unique identifier of the account,
 * the amount to be credited, a description of the transaction, and the timestamp of the last update inherited from the BaseCommand class.
 * </p>
 *
 * @see BaseCommand
 */
@Getter
public class CreditAccountCommand extends BaseCommand<String> {

    /**
     * The amount to be credited to the account.
     */
    private final BigDecimal amount;

    /**
     * A description of the credit transaction.
     */
    private final String description;

    /**
     * The timestamp indicating when the credit operation occurred.
     */
    private final LocalDateTime lastUpdate;

    /**
     * Constructs a new instance of CreditAccountCommand with the specified parameters.
     *
     * @param id           The unique identifier of the account to be credited.
     * @param amount       The amount to be credited to the account.
     * @param description  A description of the credit transaction.
     * @param lastUpdate   The timestamp indicating when the credit operation occurred.
     */
    public CreditAccountCommand(String id, BigDecimal amount, String description, LocalDateTime lastUpdate) {
        super(id);
        this.amount = amount;
        this.description = description;
        this.lastUpdate = lastUpdate;
    }
}

