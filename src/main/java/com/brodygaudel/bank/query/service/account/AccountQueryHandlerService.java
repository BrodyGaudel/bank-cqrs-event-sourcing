package com.brodygaudel.bank.query.service.account;

import com.brodygaudel.bank.common.dto.AccountResponseDTO;
import com.brodygaudel.bank.common.dto.OperationResponseDTO;
import com.brodygaudel.bank.common.query.*;
import com.brodygaudel.bank.query.entity.Account;
import com.brodygaudel.bank.query.entity.Operation;
import com.brodygaudel.bank.common.util.Mappers;
import com.brodygaudel.bank.query.repository.AccountRepository;
import com.brodygaudel.bank.query.repository.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling queries related to accounts and operations.
 */
@Service
@Slf4j
public class AccountQueryHandlerService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private final Mappers mappers;

    /**
     * Constructs an {@code AccountQueryHandlerService} with the specified repositories and mappers.
     *
     * @param accountRepository    The repository for retrieving account data.
     * @param operationRepository  The repository for retrieving operation data.
     * @param mappers              The mapper utility for converting entities to DTOs.
     */
    public AccountQueryHandlerService(AccountRepository accountRepository, OperationRepository operationRepository, Mappers mappers) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.mappers = mappers;
    }

    /**
     * Handles the {@code GetAccountByIdQuery} by retrieving and mapping an account's details by ID.
     *
     * @param query The {@code GetAccountByIdQuery} to handle.
     * @return A {@link AccountResponseDTO} containing the details of the requested account.
     */
    @QueryHandler
    public AccountResponseDTO on(@NotNull GetAccountByIdQuery query){
        log.info("# GetAccountByIdQuery handled");
        Account account = accountRepository.findById(query.getId()).orElse(null);
        if(account == null){
            log.info("# account not found");
            return null;
        }
        log.info("# account found");
        return mappers.fromAccount(account);
    }

    /**
     * Handles the {@code GetAccountByCustomerIdQuery} by retrieving and mapping an account's details by customer ID.
     *
     * @param query The {@code GetAccountByCustomerIdQuery} to handle.
     * @return A {@link AccountResponseDTO} containing the details of the requested account.
     */
    @QueryHandler
    public AccountResponseDTO on(@NotNull GetAccountByCustomerIdQuery query){
        log.info("# GetAccountByCustomerIdQuery handled");
        Account account = accountRepository.findByCustomerId(query.getCustomerId());
        if(account == null){
            log.info("# account not found");
            return null;
        }
        log.info("# account found");
        return mappers.fromAccount(account);
    }

    /**
     * Handles the {@link GetAllAccountsQuery} by retrieving and mapping details of all accounts.
     *
     * @param query The {@link GetAllAccountsQuery} to handle.
     * @return A list of {@link AccountResponseDTO} objects representing all accounts.
     */
    @QueryHandler
    public List<AccountResponseDTO> on(GetAllAccountsQuery query){
        log.info("# GetAllAccountsQuery handled");
        List<Account> accounts = accountRepository.findAll();
        if(accounts.isEmpty()){
            log.info("# account(s) not found");
            return new ArrayList<>();
        }
        log.info("# account(s) found");
        return mappers.fromListOfAccounts(accounts);
    }

    /**
     * Handles the {@code GetOperationByIdQuery} by retrieving and mapping an operation's details by ID.
     *
     * @param query The {@code GetOperationByIdQuery} to handle.
     * @return An {@link OperationResponseDTO} containing the details of the requested operation.
     */
    @QueryHandler
    public OperationResponseDTO on(@NotNull GetOperationByIdQuery query){
        log.info("# GetOperationByIdQuery handled");
        Operation operation = operationRepository.findById(query.getId()).orElse(null);
        if(operation == null){
            log.info("# operation not found");
            return null;
        }
        log.info("# operation found");
        return mappers.fromOperation(operation);
    }

    /**
     * Handles the {@code GetOperationByAccountIdQuery} by retrieving and mapping details of operations associated with an account.
     *
     * @param query The {@code GetOperationByAccountIdQuery} to handle.
     * @return A list of {@link OperationResponseDTO} objects representing operations associated with the account.
     */
    @QueryHandler
    public List<OperationResponseDTO> on(@NotNull GetOperationByAccountIdQuery query){
        log.info("# GetOperationByAccountIdQuery handled");
        Page<Operation> operationPage = operationRepository.findAllByAccountId(
                query.getAccountId(), PageRequest.of(query.getPage(), query.getSize())
        );
        List<Operation> operations = operationPage.getContent();
        if(operations.isEmpty()){
            log.info("# operation(s) not found");
            return new ArrayList<>();
        }
        log.info("# operation(s) found");
        return mappers.fromListOfOperations(operations);
    }
}

