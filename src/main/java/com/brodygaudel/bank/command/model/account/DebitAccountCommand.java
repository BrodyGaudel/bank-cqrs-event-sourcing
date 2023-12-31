package com.brodygaudel.bank.command.model.account;

import com.brodygaudel.bank.command.model.BaseCommand;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class DebitAccountCommand extends BaseCommand<String> {

    private final BigDecimal amount;
    private final String description;
    private final LocalDateTime lastUpdate;

    public DebitAccountCommand(String id, BigDecimal amount, String description, LocalDateTime lastUpdate) {
        super(id);
        this.amount = amount;
        this.description = description;
        this.lastUpdate = lastUpdate;
    }
}
