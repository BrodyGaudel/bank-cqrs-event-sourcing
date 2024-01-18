package com.brodygaudel.bank.query.service.account;

import com.brodygaudel.bank.common.event.account.*;
import com.brodygaudel.bank.common.exception.InsufficientBalanceException;
import com.brodygaudel.bank.query.entity.Account;
import com.brodygaudel.bank.query.entity.Customer;
import com.brodygaudel.bank.query.entity.Operation;
import com.brodygaudel.bank.common.enums.AccountStatus;
import com.brodygaudel.bank.common.enums.OperationType;
import com.brodygaudel.bank.common.exception.AccountNotActivatedException;
import com.brodygaudel.bank.common.exception.AccountNotFoundException;
import com.brodygaudel.bank.common.exception.CustomerAlreadyHaveAccountException;
import com.brodygaudel.bank.common.exception.CustomerNotFoundException;
import com.brodygaudel.bank.query.repository.AccountRepository;
import com.brodygaudel.bank.query.repository.CustomerRepository;
import com.brodygaudel.bank.query.repository.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service class for handling events related to accounts, such as creation, activation, suspension,
 * crediting, and debiting.
 */
@Service
@Transactional
@Slf4j
public class AccountEventHandlerService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private final CustomerRepository customerRepository;

    /**
     * Constructs an {@code AccountEventHandlerService} with the specified repositories.
     *
     * @param accountRepository    The repository for managing account data.
     * @param operationRepository  The repository for managing operation data.
     * @param customerRepository   The repository for managing customer data.
     */
    public AccountEventHandlerService(AccountRepository accountRepository, OperationRepository operationRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Handles the {@code AccountCreatedEvent} by creating a new account and associating it with a customer.
     *
     * @param event The {@code AccountCreatedEvent} to handle.
     */
    @EventHandler
    public void on(@NotNull AccountCreatedEvent event){
        log.info("# AccountCreatedEvent handled");
        Customer customer = customerRepository.findById(event.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("customer not found"));
        if(customer.getAccount() != null){
            throw new CustomerAlreadyHaveAccountException("this customer already has an account");
        }
        Account account = Account.builder()
                .id(event.getId())
                .balance(event.getBalance())
                .creation(event.getCreation())
                .status(event.getStatus())
                .lastUpdate(null)
                .customer(customer)
                .build();
        Account accountSaved = accountRepository.save(account);
        log.info("# account saved at "+accountSaved.getCreation());
    }

    /**
     * Handles the {@code AccountActivatedEvent} by updating the status of an existing account to activate.
     *
     * @param event The {@code AccountActivatedEvent} to handle.
     */
    @EventHandler
    public void on(@NotNull AccountActivatedEvent event){
        log.info("# AccountActivatedEvent handled");
        Account account = accountRepository.findById(event.getId())
                .orElseThrow(() -> new AccountNotFoundException("account not found."));
        account.setStatus(event.getStatus());
        account.setLastUpdate(event.getLastUpdate());
        Account accountActivated = accountRepository.save(account);
        log.info("# account activated at "+accountActivated.getCreation());
    }

    /**
     * Handles the {@code AccountSuspendedEvent} by updating the status of an existing account to suspended.
     *
     * @param event The {@code AccountSuspendedEvent} to handle.
     */
    @EventHandler
    public void on(@NotNull AccountSuspendedEvent event){
        log.info("# AccountSuspendedEvent handled");
        Account account = accountRepository.findById(event.getId())
                .orElseThrow(() -> new AccountNotFoundException("account not found."));
        account.setStatus(event.getStatus());
        account.setLastUpdate(event.getLastUpdate());
        Account accountSuspended = accountRepository.save(account);
        log.info("# account suspended at "+accountSuspended.getCreation());
    }

    /**
     * Handles the {@code AccountCreditedEvent} by updating the balance of an existing account and recording a credit operation.
     *
     * @param event The {@code AccountCreditedEvent} to handle.
     */
    @EventHandler
    public void on(@NotNull AccountCreditedEvent event){
        log.info("# AccountCreditedEvent handled");
        Account account = accountRepository.findById(event.getId())
                .orElseThrow(() -> new AccountNotFoundException("account not found"));
        if(!account.getStatus().equals(AccountStatus.ACTIVATED)){
            throw new AccountNotActivatedException("account not activated => "+account.getStatus());
        }else{
            account.setBalance(account.getBalance().add(event.getAmount()));
            account.setLastUpdate(event.getLastUpdate());
            Operation operation = Operation.builder()
                    .account(account)
                    .amount(event.getAmount())
                    .dateTime(event.getLastUpdate())
                    .type(OperationType.CREDIT)
                    .description(event.getDescription())
                    .id(UUID.randomUUID().toString())
                    .build();
            Operation operationSaved = operationRepository.save(operation);
            log.info("# credit operation saved at : "+operationSaved.getDateTime());
            Account accountSaved = accountRepository.save(account);
            log.info("# account credited at : "+accountSaved.getLastUpdate());
        }
    }

    /**
     * Handles the {@code AccountDebitedEvent} by updating the balance of an existing account,
     * recording a debit operation, and checking for sufficient balance.
     *
     * @param event The {@code AccountDebitedEvent} to handle.
     */
    @EventHandler
    public void on(@NotNull AccountDebitedEvent event) {
        log.info("# AccountDebitedEvent handled");
        Account account = accountRepository.findById(event.getId())
                .orElseThrow(() -> new AccountNotFoundException("account not found"));
        if(!account.getStatus().equals(AccountStatus.ACTIVATED)){
            throw new AccountNotActivatedException("account not activated => "+account.getStatus());
        } else if (account.getBalance().compareTo(event.getAmount()) < 0) {
            throw new InsufficientBalanceException("Balance not sufficient");
        }else{
            account.setBalance(account.getBalance().subtract(event.getAmount()));
            account.setLastUpdate(event.getLastUpdate());
            Operation operation = Operation.builder()
                    .account(account)
                    .amount(event.getAmount())
                    .dateTime(event.getLastUpdate())
                    .type(OperationType.DEBIT)
                    .description(event.getDescription())
                    .id(UUID.randomUUID().toString())
                    .build();
            Operation operationSaved = operationRepository.save(operation);
            log.info("# debit operation saved at : "+operationSaved.getDateTime());
            Account accountSaved = accountRepository.save(account);
            log.info("# account debited at : "+accountSaved.getLastUpdate());
        }
    }
}

