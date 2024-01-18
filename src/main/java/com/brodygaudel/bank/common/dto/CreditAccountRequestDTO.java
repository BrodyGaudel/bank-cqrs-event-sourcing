package com.brodygaudel.bank.common.dto;

import java.math.BigDecimal;

public record CreditAccountRequestDTO(String id, String description, BigDecimal amount) {
}
