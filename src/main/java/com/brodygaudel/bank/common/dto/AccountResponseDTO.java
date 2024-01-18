package com.brodygaudel.bank.common.dto;

import com.brodygaudel.bank.common.enums.AccountStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class AccountResponseDTO {
    private String id;
    private BigDecimal balance;
    private AccountStatus status;
    private String customerId;
    private LocalDateTime creation;
    private LocalDateTime lastUpdate;
}
