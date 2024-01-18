package com.brodygaudel.bank.common.dto;

import com.brodygaudel.bank.common.enums.OperationType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class OperationResponseDTO {
    private String id;
    private OperationType type;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private String description;
    private String accountId;
}
