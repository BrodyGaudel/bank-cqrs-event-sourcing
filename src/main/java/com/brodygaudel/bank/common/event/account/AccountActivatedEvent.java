package com.brodygaudel.bank.common.event.account;

import com.brodygaudel.bank.common.event.BaseEvent;
import com.brodygaudel.bank.query.enums.AccountStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AccountActivatedEvent extends BaseEvent<String> {

    private final AccountStatus status;
    private final LocalDateTime lastUpdate;

    public AccountActivatedEvent(String id, AccountStatus status, LocalDateTime lastUpdate) {
        super(id);
        this.status = status;
        this.lastUpdate = lastUpdate;
    }
}
