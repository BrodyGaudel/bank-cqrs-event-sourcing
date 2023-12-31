package com.brodygaudel.bank.common.event.account;

import com.brodygaudel.bank.common.event.BaseEvent;
import com.brodygaudel.bank.query.enums.AccountStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class AccountCreatedEvent extends BaseEvent<String> {
    private final BigDecimal balance;
    private final AccountStatus status;
    private final LocalDateTime creation;
    private final String customerId;

    public AccountCreatedEvent(String id, BigDecimal balance, AccountStatus status, LocalDateTime creation, String customerId) {
        super(id);
        this.balance = balance;
        this.status = status;
        this.creation = creation;
        this.customerId = customerId;
    }
}
