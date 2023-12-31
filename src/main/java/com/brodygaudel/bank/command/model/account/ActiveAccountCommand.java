package com.brodygaudel.bank.command.model.account;

import com.brodygaudel.bank.command.model.BaseCommand;
import com.brodygaudel.bank.query.enums.AccountStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ActiveAccountCommand extends BaseCommand<String> {

    private final AccountStatus status;
    private final LocalDateTime lastUpdate;

    public ActiveAccountCommand(String id, AccountStatus status, LocalDateTime lastUpdate) {
        super(id);
        this.status = status;
        this.lastUpdate = lastUpdate;
    }
}
