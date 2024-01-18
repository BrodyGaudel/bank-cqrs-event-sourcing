package com.brodygaudel.bank.common.command.account;

import com.brodygaudel.bank.common.command.BaseCommand;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Command to debit an amount from an account.
 *
 * <p>
 * This command is used to initiate a debit operation on an account. It includes the unique identifier of the account,
 * the amount to be debited, a description of the transaction, and the timestamp of the last update inherited from the BaseCommand class.
 * </p>
 *
 * @see BaseCommand
 */
@Getter
public class DebitAccountCommand extends BaseCommand<String> {

    /**
     * The amount to be debited from the account.
     */
    private final BigDecimal amount;

    /**
     * A description of the debit transaction.
     */
    private final String description;

    /**
     * The timestamp indicating when the debit operation occurred.
     */
    private final LocalDateTime lastUpdate;

    /**
     * Constructs a new instance of DebitAccountCommand with the specified parameters.
     *
     * @param id           The unique identifier of the account to be debited.
     * @param amount       The amount to be debited from the account.
     * @param description  A description of the debit transaction.
     * @param lastUpdate   The timestamp indicating when the debit operation occurred.
     */
    public DebitAccountCommand(String id, BigDecimal amount, String description, LocalDateTime lastUpdate) {
        super(id);
        this.amount = amount;
        this.description = description;
        this.lastUpdate = lastUpdate;
    }
}

