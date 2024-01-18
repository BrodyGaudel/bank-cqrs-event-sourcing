package com.brodygaudel.bank.common.dto;

import java.math.BigDecimal;

public record DebitAccountRequestDTO(String id, String description, BigDecimal amount) {
}
