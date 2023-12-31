package com.brodygaudel.bank.common.event.account;

import com.brodygaudel.bank.common.event.BaseEvent;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class AccountDebitedEvent extends BaseEvent<String> {

    private final BigDecimal amount;
    private final String description;
    private final LocalDateTime lastUpdate;

    public AccountDebitedEvent(String id, BigDecimal amount, String description, LocalDateTime lastUpdate) {
        super(id);
        this.amount = amount;
        this.description = description;
        this.lastUpdate = lastUpdate;
    }
}
