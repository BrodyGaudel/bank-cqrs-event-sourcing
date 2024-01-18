package com.brodygaudel.bank.query.controller;

import com.brodygaudel.bank.common.dto.AccountResponseDTO;
import com.brodygaudel.bank.common.dto.OperationResponseDTO;
import com.brodygaudel.bank.common.query.*;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling account-related queries.
 *
 * <p>
 * This controller provides endpoints for querying account information, including getting an account by ID or customer ID,
 * retrieving a list of all accounts, and getting operations by account ID. It uses the Query Gateway to dispatch queries
 * to the appropriate handlers and retrieve the query results.
 * </p>
 */
@RestController
@RequestMapping("/queries/accounts")
public class AccountQueryRestController {

    private final QueryGateway queryGateway;

    /**
     * Constructs a new instance of AccountQueryRestController.
     *
     * @param queryGateway The Query Gateway used for dispatching queries.
     */
    public AccountQueryRestController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    /**
     * Endpoint for retrieving account information by ID.
     *
     * @param accountId The unique identifier of the account to retrieve.
     * @return An AccountResponseDTO containing the information of the specified account.
     */
    @GetMapping("/get-account-by-id/{accountId}")
    public AccountResponseDTO getAccountById(@PathVariable String accountId) {
        return queryGateway.query(
                new GetAccountByIdQuery(accountId),
                ResponseTypes.instanceOf(AccountResponseDTO.class)
        ).join();
    }

    /**
     * Endpoint for retrieving account information by customer ID.
     *
     * @param customerId The unique identifier of the customer associated with the account.
     * @return An AccountResponseDTO containing the information of the account associated with the specified customer.
     */
    @GetMapping("/get-account-by-customer-id/{customerId}")
    public AccountResponseDTO getAccountByCustomerId(@PathVariable String customerId) {
        return queryGateway.query(
                new GetAccountByCustomerIdQuery(customerId),
                ResponseTypes.instanceOf(AccountResponseDTO.class)
        ).join();
    }

    /**
     * Endpoint for retrieving a list of all accounts.
     *
     * @return A list of AccountResponseDTO objects representing all accounts.
     */
    @GetMapping("/get-all-accounts")
    public List<AccountResponseDTO> getAllAccounts() {
        return queryGateway.query(
                new GetAllAccountsQuery(),
                ResponseTypes.multipleInstancesOf(AccountResponseDTO.class)
        ).join();
    }

    /**
     * Endpoint for retrieving operation information by operation ID.
     *
     * @param operationId The unique identifier of the operation to retrieve.
     * @return An OperationResponseDTO containing the information of the specified operation.
     */
    @GetMapping("/get-operation-by-id/{operationId}")
    public OperationResponseDTO getOperationById(@PathVariable String operationId) {
        return queryGateway.query(
                new GetOperationByIdQuery(operationId),
                ResponseTypes.instanceOf(OperationResponseDTO.class)
        ).join();
    }

    /**
     * Endpoint for retrieving a list of operations by account ID with pagination support.
     *
     * @param accountId The unique identifier of the account for which to retrieve operations.
     * @param page      The page number for paginated results (default is 0).
     * @param size      The size of each page in paginated results (default is 100).
     * @return A list of OperationResponseDTO objects representing operations associated with the specified account.
     */
    @GetMapping("/get-operation-by-account-id/{accountId}")
    public List<OperationResponseDTO> getOperationByAccountId(@PathVariable String accountId,
                                                              @RequestParam(name = "page", defaultValue = "0") int page,
                                                              @RequestParam(name = "size", defaultValue = "100") int size) {
        return queryGateway.query(
                new GetOperationByAccountIdQuery(accountId, page, size),
                ResponseTypes.multipleInstancesOf(OperationResponseDTO.class)
        ).join();
    }

    /**
     * Exception handler for handling exceptions that may occur during query processing.
     *
     * @param exception The exception that occurred.
     * @return A ResponseEntity with an error message and HTTP status code 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

