package com.brodygaudel.bank.command.dto;

import java.math.BigDecimal;

public record DebitAccountRequestDTO(String id, String description, BigDecimal amount) {
}
