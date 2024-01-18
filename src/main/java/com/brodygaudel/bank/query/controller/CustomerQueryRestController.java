package com.brodygaudel.bank.query.controller;

import com.brodygaudel.bank.common.dto.CustomerResponseDTO;
import com.brodygaudel.bank.common.query.GetAllCustomersQuery;
import com.brodygaudel.bank.common.query.GetCustomerByIdQuery;
import com.brodygaudel.bank.common.query.SearchCustomersQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling customer-related queries.
 *
 * <p>
 * This controller provides endpoints for querying customer information, including getting a customer by ID,
 * searching for customers based on a keyword, and retrieving a list of all customers. It uses the Query Gateway
 * to dispatch queries to the appropriate handlers and retrieve the query results.
 * </p>
 */
@RestController
@RequestMapping("/queries/customers")
public class CustomerQueryRestController {

    private final QueryGateway queryGateway;

    /**
     * Constructs a new instance of CustomerQueryRestController.
     *
     * @param queryGateway The Query Gateway used for dispatching queries.
     */
    public CustomerQueryRestController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    /**
     * Endpoint for retrieving customer information by ID.
     *
     * @param id The unique identifier of the customer to retrieve.
     * @return A CustomerResponseDTO containing the information of the specified customer.
     */
    @GetMapping("/get/{id}")
    public CustomerResponseDTO getById(@PathVariable String id) {
        return queryGateway.query(new GetCustomerByIdQuery(id),
                ResponseTypes.instanceOf(CustomerResponseDTO.class)
        ).join();
    }

    /**
     * Endpoint for searching customers based on a keyword.
     *
     * @param keyword The keyword to search for in customer information.
     * @param page    The page number for paginated results (default is 0).
     * @param size    The size of each page in paginated results (default is 100).
     * @return A list of CustomerResponseDTO objects matching the search criteria.
     */
    @GetMapping("/search")
    public List<CustomerResponseDTO> search(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                            @RequestParam(name = "size", defaultValue = "100") int size) {

        return queryGateway.query(new SearchCustomersQuery(keyword, page, size),
                ResponseTypes.multipleInstancesOf(CustomerResponseDTO.class)
        ).join();
    }

    /**
     * Endpoint for retrieving a list of all customers.
     *
     * @return A list of CustomerResponseDTO objects representing all customers.
     */
    @GetMapping("/list")
    public List<CustomerResponseDTO> getAll() {
        return queryGateway.query(new GetAllCustomersQuery(),
                ResponseTypes.multipleInstancesOf(CustomerResponseDTO.class)
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

