package com.brodygaudel.bank.query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SearchCustomersQuery {
    private String keyword;
    private int page;
    private int size;
}
