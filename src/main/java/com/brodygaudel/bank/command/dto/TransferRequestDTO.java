package com.brodygaudel.bank.command.dto;

import java.math.BigDecimal;

public record TransferRequestDTO(String idFrom, String idTo, String description, BigDecimal amount) {
}
