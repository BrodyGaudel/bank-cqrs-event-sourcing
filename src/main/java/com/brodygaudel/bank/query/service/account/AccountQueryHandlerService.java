package com.brodygaudel.bank.query.service.account;

import com.brodygaudel.bank.query.dto.AccountResponseDTO;
import com.brodygaudel.bank.query.dto.OperationResponseDTO;
import com.brodygaudel.bank.query.entity.Account;
import com.brodygaudel.bank.query.entity.Operation;
import com.brodygaudel.bank.query.model.*;
import com.brodygaudel.bank.query.util.Mappers;
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

@Service
@Slf4j
public class AccountQueryHandlerService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private final Mappers mappers;

    public AccountQueryHandlerService(AccountRepository accountRepository, OperationRepository operationRepository, Mappers mappers) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.mappers = mappers;
    }


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
