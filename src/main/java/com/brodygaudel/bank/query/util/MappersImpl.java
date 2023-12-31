package com.brodygaudel.bank.query.util;

import com.brodygaudel.bank.query.dto.AccountResponseDTO;
import com.brodygaudel.bank.query.dto.CustomerResponseDTO;
import com.brodygaudel.bank.query.dto.OperationResponseDTO;
import com.brodygaudel.bank.query.entity.Account;
import com.brodygaudel.bank.query.entity.Customer;
import com.brodygaudel.bank.query.entity.Operation;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappersImpl implements Mappers{
    @Override
    public CustomerResponseDTO fromCustomer(@NotNull Customer customer) {
        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .nic(customer.getNic())
                .firstname(customer.getFirstname())
                .name(customer.getName())
                .placeOfBirth(customer.getPlaceOfBirth())
                .dateOfBirth(customer.getDateOfBirth())
                .nationality(customer.getNationality())
                .sex(customer.getSex())
                .creation(customer.getCreation())
                .lastUpdate(customer.getLastUpdate())
                .build();
    }

    @Override
    public List<CustomerResponseDTO> fromListOfCustomers(@NotNull List<Customer> customers) {
        return customers.stream().map(this::fromCustomer).toList();
    }

    @Override
    public AccountResponseDTO fromAccount(@NotNull Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .customerId(account.getCustomer().getId())
                .status(account.getStatus())
                .creation(account.getCreation())
                .lastUpdate(account.getLastUpdate())
                .build();
    }

    @Override
    public List<AccountResponseDTO> fromListOfAccounts(@NotNull List<Account> accounts) {
        return accounts.stream().map(this::fromAccount).toList();
    }

    @Override
    public OperationResponseDTO fromOperation(@NotNull Operation operation) {
        return OperationResponseDTO.builder()
                .type(operation.getType())
                .accountId(operation.getAccount().getId())
                .amount(operation.getAmount())
                .dateTime(operation.getDateTime())
                .description(operation.getDescription())
                .id(operation.getId())
                .build();
    }

    @Override
    public List<OperationResponseDTO> fromListOfOperations(@NotNull List<Operation> operations) {
        return operations.stream().map(this::fromOperation).toList();
    }

}
