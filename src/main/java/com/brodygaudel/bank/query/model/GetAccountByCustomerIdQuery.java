package com.brodygaudel.bank.query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetAccountByCustomerIdQuery {
    private String customerId;
}
