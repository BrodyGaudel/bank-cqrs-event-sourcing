package com.brodygaudel.bank.command.dto;

import com.brodygaudel.bank.query.enums.AccountStatus;

public record AccountStatusUpdatedDTO(String accountId, AccountStatus status) {
}
