package com.brodygaudel.bank.command.model.account;

import com.brodygaudel.bank.command.model.BaseCommand;
import com.brodygaudel.bank.query.enums.AccountStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CreateAccountCommand extends BaseCommand<String> {
    private final BigDecimal balance;
    private final AccountStatus status;
    private final LocalDateTime creation;
    private final String customerId;

    public CreateAccountCommand(String id, BigDecimal balance, AccountStatus status, LocalDateTime creation, String customerId) {
        super(id);
        this.balance = balance;
        this.status = status;
        this.creation = creation;
        this.customerId = customerId;
    }
}
