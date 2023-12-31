package com.brodygaudel.bank.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomerPageResponseDTO {
    private int page;
    private int totalPage;
    private int size;
    private List<CustomerResponseDTO> customers;
}
