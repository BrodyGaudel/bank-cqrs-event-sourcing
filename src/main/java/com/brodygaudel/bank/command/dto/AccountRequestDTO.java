package com.brodygaudel.bank.command.dto;

import com.brodygaudel.bank.query.enums.AccountStatus;

public record AccountRequestDTO(String customerId, AccountStatus status) {
}
