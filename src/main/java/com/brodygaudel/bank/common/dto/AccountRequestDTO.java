package com.brodygaudel.bank.common.dto;

import com.brodygaudel.bank.common.enums.AccountStatus;

public record AccountRequestDTO(String customerId, AccountStatus status) {
}
