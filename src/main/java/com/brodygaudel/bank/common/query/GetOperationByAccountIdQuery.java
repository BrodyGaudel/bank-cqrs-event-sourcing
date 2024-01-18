package com.brodygaudel.bank.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetOperationByAccountIdQuery {
    private String accountId;
    private int page;
    private int size;
}
