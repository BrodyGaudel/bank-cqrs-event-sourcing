package com.brodygaudel.bank.query.util;

import com.brodygaudel.bank.common.event.customer.CustomerCreatedEvent;
import com.brodygaudel.bank.query.dto.AccountResponseDTO;
import com.brodygaudel.bank.query.dto.CustomerResponseDTO;
import com.brodygaudel.bank.query.dto.OperationResponseDTO;
import com.brodygaudel.bank.query.entity.Account;
import com.brodygaudel.bank.query.entity.Customer;
import com.brodygaudel.bank.query.entity.Operation;

import java.util.List;

public interface Mappers {
    CustomerResponseDTO fromCustomer(Customer customer);
    List<CustomerResponseDTO> fromListOfCustomers(List<Customer> customers);
    AccountResponseDTO fromAccount(Account account);
    List<AccountResponseDTO> fromListOfAccounts(List<Account> accounts);

    OperationResponseDTO fromOperation(Operation operation);
    List<OperationResponseDTO> fromListOfOperations(List<Operation> operations);

}
