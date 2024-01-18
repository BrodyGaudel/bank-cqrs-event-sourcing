package com.brodygaudel.bank.query.service.account;

import com.brodygaudel.bank.common.event.account.*;
import com.brodygaudel.bank.query.entity.Account;
import com.brodygaudel.bank.query.entity.Customer;
import com.brodygaudel.bank.query.entity.Operation;
import com.brodygaudel.bank.common.enums.AccountStatus;
import com.brodygaudel.bank.common.enums.OperationType;
import com.brodygaudel.bank.common.enums.Sex;
import com.brodygaudel.bank.query.repository.AccountRepository;
import com.brodygaudel.bank.query.repository.CustomerRepository;
import com.brodygaudel.bank.query.repository.OperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountEventHandlerServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AccountEventHandlerService service;

    @BeforeEach
    void setUp() {
        service = new AccountEventHandlerService(accountRepository, operationRepository, customerRepository);
    }

    @Test
    void onAccountCreatedEvent() {
        AccountCreatedEvent event = new AccountCreatedEvent(
                "id", BigDecimal.valueOf(888), AccountStatus.ACTIVATED, LocalDateTime.now(), "customerId"
        );

        Customer customer = Customer.builder().nic("NIC").sex(Sex.M).name("Doe").firstname("John")
                .nationality("Gabon").id("customerId").creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now())
                .build();
        Account account = Account.builder().id(event.getId()).balance(event.getBalance()).creation(event.getCreation())
                .status(event.getStatus()).lastUpdate(null).customer(customer)
                .build();

        when(customerRepository.findById(event.getCustomerId())).thenReturn(Optional.of(customer));
        when(accountRepository.save(any())).thenReturn(account);
        service.on(event);
        verify(customerRepository, times(1)).findById(anyString());
        verify(accountRepository, times(1)).save(any());
    }

    @Test
    void onAccountActivatedEvent() {
        AccountActivatedEvent event = new AccountActivatedEvent(
                "id", AccountStatus.ACTIVATED, LocalDateTime.now()
        );
        Account account = Account.builder().id(event.getId()).balance(BigDecimal.valueOf(333)).creation(LocalDateTime.now())
                .status(event.getStatus()).lastUpdate(null).customer(new Customer())
                .build();
        when(accountRepository.findById(event.getId())).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenReturn(account);

        service.on(event);
        verify(accountRepository, times(1)).findById(anyString());
        verify(accountRepository, times(1)).save(any());
    }

    @Test
    void onAccountSuspendedEvent() {
        AccountSuspendedEvent event = new AccountSuspendedEvent(
                "id", AccountStatus.SUSPENDED, LocalDateTime.now()
        );
        Account account = Account.builder().id(event.getId()).balance(BigDecimal.valueOf(333)).creation(LocalDateTime.now())
                .status(event.getStatus()).lastUpdate(null).customer(new Customer())
                .build();
        when(accountRepository.findById(event.getId())).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenReturn(account);

        service.on(event);
        verify(accountRepository, times(1)).findById(anyString());
        verify(accountRepository, times(1)).save(any());
    }

    @Test
    void onAccountCreditedEvent() {
        AccountCreditedEvent event =  new AccountCreditedEvent(
                "id", BigDecimal.valueOf(444), "credit", LocalDateTime.now()
        );
        Account account = Account.builder().id(event.getId()).balance(BigDecimal.valueOf(333)).creation(LocalDateTime.now())
                .status(AccountStatus.ACTIVATED).lastUpdate(LocalDateTime.now()).customer(new Customer())
                .build();
        Operation operation = Operation.builder().account(account).amount(event.getAmount()).dateTime(event.getLastUpdate())
                .type(OperationType.CREDIT).description(event.getDescription()).id(UUID.randomUUID().toString())
                .build();
        when(accountRepository.findById(event.getId())).thenReturn(Optional.of(account));
        when(operationRepository.save(any())).thenReturn(operation);
        when(accountRepository.save(any())).thenReturn(account);
        service.on(event);
        verify(accountRepository, times(1)).findById(anyString());
        verify(operationRepository, times(1)).save(any());
        verify(accountRepository, times(1)).save(any());
    }

    @Test
    void onAccountDebitedEvent() {
        AccountDebitedEvent event =  new AccountDebitedEvent(
                "id", BigDecimal.valueOf(111), "débit", LocalDateTime.now()
        );
        Account account = Account.builder().id(event.getId()).balance(BigDecimal.valueOf(333)).creation(LocalDateTime.now())
                .status(AccountStatus.ACTIVATED).lastUpdate(LocalDateTime.now()).customer(new Customer())
                .build();
        Operation operation = Operation.builder().account(account).amount(event.getAmount()).dateTime(event.getLastUpdate())
                .type(OperationType.DEBIT).description(event.getDescription()).id(UUID.randomUUID().toString())
                .build();
        when(accountRepository.findById(event.getId())).thenReturn(Optional.of(account));
        when(operationRepository.save(any())).thenReturn(operation);
        when(accountRepository.save(any())).thenReturn(account);
        service.on(event);
        verify(accountRepository, times(1)).findById(anyString());
        verify(operationRepository, times(1)).save(any());
        verify(accountRepository, times(1)).save(any());
    }
}