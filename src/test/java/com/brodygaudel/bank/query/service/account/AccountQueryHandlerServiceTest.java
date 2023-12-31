package com.brodygaudel.bank.query.service.account;

import com.brodygaudel.bank.query.dto.AccountResponseDTO;
import com.brodygaudel.bank.query.dto.OperationResponseDTO;
import com.brodygaudel.bank.query.entity.Account;
import com.brodygaudel.bank.query.entity.Customer;
import com.brodygaudel.bank.query.entity.Operation;
import com.brodygaudel.bank.query.enums.AccountStatus;
import com.brodygaudel.bank.query.enums.OperationType;
import com.brodygaudel.bank.query.model.*;
import com.brodygaudel.bank.query.repository.AccountRepository;
import com.brodygaudel.bank.query.repository.OperationRepository;
import com.brodygaudel.bank.query.util.Mappers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccountQueryHandlerServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private Mappers mappers;

    @InjectMocks
    private AccountQueryHandlerService service;

    @BeforeEach
    void setUp() {
        service = new AccountQueryHandlerService(accountRepository, operationRepository, mappers);
    }

    @Test
    void onGetAccountByIdQuery() {
        GetAccountByIdQuery query = new GetAccountByIdQuery("id");
        Account account = Account.builder().customer(new Customer()).id(query.getId()).balance(BigDecimal.valueOf(999))
                .status(AccountStatus.ACTIVATED).creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now())
                .build();
        AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder().id(query.getId()).balance(BigDecimal.valueOf(999))
                .status(AccountStatus.ACTIVATED).creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now())
                .build();

        when(accountRepository.findById(query.getId())).thenReturn(Optional.of(account));
        when(mappers.fromAccount(account)).thenReturn(accountResponseDTO);

        AccountResponseDTO response = service.on(query);
        assertNotNull(response);
        assertEquals(query.getId(), response.getId());
    }

    @Test
    void onGetAccountByCustomerIdQuery() {
        GetAccountByCustomerIdQuery query = new GetAccountByCustomerIdQuery("customerId");
        Account account = Account.builder().customer(new Customer()).id("ID").balance(BigDecimal.valueOf(999))
                .status(AccountStatus.ACTIVATED).creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now())
                .build();
        AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder().id(account.getId()).balance(BigDecimal.valueOf(999))
                .status(AccountStatus.ACTIVATED).creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now()).customerId(query.getCustomerId())
                .build();

        when(accountRepository.findByCustomerId(query.getCustomerId())).thenReturn(account);
        when(mappers.fromAccount(account)).thenReturn(accountResponseDTO);
        AccountResponseDTO response = service.on(query);
        assertNotNull(response);
        assertEquals(query.getCustomerId(), response.getCustomerId());
    }

    @Test
    void onGetAllAccountsQuery() {
        GetAllAccountsQuery query = new GetAllAccountsQuery();
        Account account1 = Account.builder().customer(new Customer()).id("ID1").balance(BigDecimal.valueOf(999))
                .status(AccountStatus.ACTIVATED).creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now())
                .build();
        Account account2 = Account.builder().customer(new Customer()).id("ID2").balance(BigDecimal.valueOf(999))
                .status(AccountStatus.ACTIVATED).creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now())
                .build();
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        AccountResponseDTO dto1 = AccountResponseDTO.builder().id("ID1").balance(BigDecimal.valueOf(999))
                .status(AccountStatus.ACTIVATED).creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now()).customerId("CUSTOMER_ID_1")
                .build();
        AccountResponseDTO dto2 = AccountResponseDTO.builder().id("ID2").balance(BigDecimal.valueOf(999))
                .status(AccountStatus.ACTIVATED).creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now()).customerId("CUSTOMER_ID_2")
                .build();

        when(accountRepository.findAll()).thenReturn(accounts);
        when(mappers.fromListOfAccounts(accounts)).thenReturn(List.of(dto1,dto2));

        List<AccountResponseDTO> response = service.on(query);
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
    }

    @Test
    void onGetOperationByIdQuery() {
        GetOperationByIdQuery query = new GetOperationByIdQuery("id");
        Operation operation = Operation.builder().type(OperationType.CREDIT).description("CREDIT").account(new Account())
                .id(query.getId()).dateTime(LocalDateTime.now()).amount(BigDecimal.valueOf(777))
                .build();

        OperationResponseDTO operationResponseDTO = OperationResponseDTO.builder().accountId("accountId").type(operation.getType())
                .description(operation.getDescription()).dateTime(operation.getDateTime()).amount(operation.getAmount())
                .id(query.getId())
                .build();

        when(operationRepository.findById(query.getId())).thenReturn(Optional.of(operation));
        when(mappers.fromOperation(operation)).thenReturn(operationResponseDTO);

        OperationResponseDTO response = service.on(query);
        assertNotNull(response);
        assertEquals(response.getId(), query.getId());
    }

    @Test
    void onGetOperationByAccountIdQuery() {
        GetOperationByAccountIdQuery query = new GetOperationByAccountIdQuery("accountId", 0, 2);

        Operation operation1 = Operation.builder().type(OperationType.CREDIT).description("CREDIT").account(new Account())
                .id("ID1").dateTime(LocalDateTime.now()).amount(BigDecimal.valueOf(777))
                .build();
        Operation operation2 = Operation.builder().type(OperationType.CREDIT).description("CREDIT").account(new Account())
                .id("ID2").dateTime(LocalDateTime.now()).amount(BigDecimal.valueOf(777))
                .build();
        List<Operation> operations = new ArrayList<>();
        operations.add(operation1);
        operations.add(operation2);

        OperationResponseDTO dto1 = OperationResponseDTO.builder().accountId(query.getAccountId()).type(operation1.getType())
                .description(operation1.getDescription()).dateTime(operation1.getDateTime()).amount(operation1.getAmount())
                .id("ID1")
                .build();
        OperationResponseDTO dto2 = OperationResponseDTO.builder().accountId(query.getAccountId()).type(operation2.getType())
                .description(operation2.getDescription()).dateTime(operation2.getDateTime()).amount(operation2.getAmount())
                .id("ID2")
                .build();


        when(operationRepository.findAllByAccountId(query.getAccountId(), PageRequest.of(query.getPage(), query.getSize())))
                .thenReturn( new PageImpl<>(operations, PageRequest.of(query.getPage(), query.getSize()), 2));
        when(mappers.fromListOfOperations(operations)).thenReturn(List.of(dto1, dto2));

        List<OperationResponseDTO> response = service.on(query);
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
    }
}