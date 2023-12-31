package com.brodygaudel.bank.command.dto;

import java.math.BigDecimal;

public record CreditAccountRequestDTO(String id, String description, BigDecimal amount) {
}
