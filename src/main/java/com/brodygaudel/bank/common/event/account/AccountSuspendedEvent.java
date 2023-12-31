package com.brodygaudel.bank.common.event.account;

import com.brodygaudel.bank.common.event.BaseEvent;
import com.brodygaudel.bank.query.enums.AccountStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AccountSuspendedEvent extends BaseEvent<String> {

    private final AccountStatus status;
    private final LocalDateTime lastUpdate;

    public AccountSuspendedEvent(String id, AccountStatus status, LocalDateTime lastUpdate) {
        super(id);
        this.status = status;
        this.lastUpdate = lastUpdate;
    }
}
