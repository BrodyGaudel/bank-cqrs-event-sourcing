package com.brodygaudel.bank.common.dto;

import java.math.BigDecimal;

public record TransferRequestDTO(String idFrom, String idTo, String description, BigDecimal amount) {
}
