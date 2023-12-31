package com.brodygaudel.bank.query.controller;

import com.brodygaudel.bank.query.dto.AccountResponseDTO;
import com.brodygaudel.bank.query.dto.OperationResponseDTO;
import com.brodygaudel.bank.query.model.*;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queries/accounts")
public class AccountQueryRestController {

    private final QueryGateway queryGateway;

    public AccountQueryRestController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/get-account-by-id/{accountId}")
    public AccountResponseDTO getAccountById(@PathVariable String accountId){
        return queryGateway.query(
                new GetAccountByIdQuery(accountId),
                ResponseTypes.instanceOf(AccountResponseDTO.class)
        ).join();
    }

    @GetMapping("/get-account-by-customer-id/{customerId}")
    public AccountResponseDTO getAccountByCustomerId(@PathVariable String customerId){
        return queryGateway.query(
                new GetAccountByCustomerIdQuery(customerId),
                ResponseTypes.instanceOf(AccountResponseDTO.class)
        ).join();
    }

    @GetMapping("/get-all-accounts")
    public List<AccountResponseDTO> getAllAccounts(){
        return queryGateway.query(
                new GetAllAccountsQuery(),
                ResponseTypes.multipleInstancesOf(AccountResponseDTO.class)
        ).join();
    }

    @GetMapping("/get-operation-by-id/{operationId}")
    public OperationResponseDTO getOperationById(@PathVariable String operationId){
        return queryGateway.query(
                new GetOperationByIdQuery(operationId),
                ResponseTypes.instanceOf(OperationResponseDTO.class)
        ).join();
    }

    @GetMapping("/get-operation-by-account-id/{accountId}")
    public List<OperationResponseDTO> getOperationByAccountId(@PathVariable String accountId,
                                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                                               @RequestParam(name = "size", defaultValue = "100") int size){
        return queryGateway.query(
                new GetOperationByAccountIdQuery(accountId, page, size),
                ResponseTypes.multipleInstancesOf(OperationResponseDTO.class)
        ).join();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
