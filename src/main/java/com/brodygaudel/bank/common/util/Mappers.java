package com.brodygaudel.bank.common.util;

import com.brodygaudel.bank.common.dto.AccountResponseDTO;
import com.brodygaudel.bank.common.dto.CustomerResponseDTO;
import com.brodygaudel.bank.common.dto.OperationResponseDTO;
import com.brodygaudel.bank.query.entity.Account;
import com.brodygaudel.bank.query.entity.Customer;
import com.brodygaudel.bank.query.entity.Operation;

import java.util.List;

/**
 * Interface defining mapping methods to convert domain entities to DTOs (Data Transfer Objects).
 *
 * <p>
 * Implementations of this interface provide methods for converting various domain entities, such as Customer,
 * Account, and Operation, into their corresponding DTO representations.
 * </p>
 */
public interface Mappers {

    /**
     * Converts a Customer entity to a CustomerResponseDTO.
     *
     * @param customer The Customer entity to be converted.
     * @return The corresponding CustomerResponseDTO.
     */
    CustomerResponseDTO fromCustomer(Customer customer);

    /**
     * Converts a list of Customer entities to a list of CustomerResponseDTOs.
     *
     * @param customers The list of Customer entities to be converted.
     * @return The corresponding list of CustomerResponseDTOs.
     */
    List<CustomerResponseDTO> fromListOfCustomers(List<Customer> customers);

    /**
     * Converts an Account entity to an AccountResponseDTO.
     *
     * @param account The Account entity to be converted.
     * @return The corresponding AccountResponseDTO.
     */
    AccountResponseDTO fromAccount(Account account);

    /**
     * Converts a list of Account entities to a list of AccountResponseDTOs.
     *
     * @param accounts The list of Account entities to be converted.
     * @return The corresponding list of AccountResponseDTOs.
     */
    List<AccountResponseDTO> fromListOfAccounts(List<Account> accounts);

    /**
     * Converts an Operation entity to an OperationResponseDTO.
     *
     * @param operation The Operation entity to be converted.
     * @return The corresponding OperationResponseDTO.
     */
    OperationResponseDTO fromOperation(Operation operation);

    /**
     * Converts a list of Operation entities to a list of OperationResponseDTOs.
     *
     * @param operations The list of Operation entities to be converted.
     * @return The corresponding list of OperationResponseDTOs.
     */
    List<OperationResponseDTO> fromListOfOperations(List<Operation> operations);
}

