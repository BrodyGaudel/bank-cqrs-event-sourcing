package com.brodygaudel.bank.query.service.customer;

import com.brodygaudel.bank.common.dto.CustomerResponseDTO;
import com.brodygaudel.bank.query.entity.Customer;
import com.brodygaudel.bank.common.exception.CustomerNotFoundException;
import com.brodygaudel.bank.common.util.Mappers;
import com.brodygaudel.bank.common.query.GetAllCustomersQuery;
import com.brodygaudel.bank.common.query.GetCustomerByIdQuery;
import com.brodygaudel.bank.common.query.SearchCustomersQuery;
import com.brodygaudel.bank.query.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling customer-related queries and providing query responses.
 */
@Service
@Slf4j
public class CustomerQueryHandlerService {

    private final CustomerRepository customerRepository;
    private final Mappers mappers;

    /**
     * Constructs a {@code CustomerQueryHandlerService} with the specified {@code CustomerRepository}
     * and {@code Mappers} instance.
     *
     * @param customerRepository The repository for retrieving customer data.
     * @param mappers            The mapper utility for converting entities to DTOs.
     */
    public CustomerQueryHandlerService(CustomerRepository customerRepository, Mappers mappers) {
        this.customerRepository = customerRepository;
        this.mappers = mappers;
    }

    /**
     * Handles the {@code GetCustomerByIdQuery} by retrieving and mapping a customer's details by ID.
     *
     * @param query The {@code GetCustomerByIdQuery} to handle.
     * @return A {@link CustomerResponseDTO} containing the details of the requested customer.
     * @throws CustomerNotFoundException if no customer with the given ID is found.
     */
    @QueryHandler
    public CustomerResponseDTO on(@NotNull GetCustomerByIdQuery query){
        log.info("GetCustomerByIdQuery handled");
        Customer customer = customerRepository.findById(query.getId())
                .orElseThrow(() -> new CustomerNotFoundException("customer with id '" + query.getId() + "' not found."));
        log.info("customer found");
        return mappers.fromCustomer(customer);
    }

    /**
     * Handles the {@code SearchCustomersQuery} by performing a keyword search and mapping the results.
     *
     * @param query The {@code SearchCustomersQuery} to handle.
     * @return A list of {@link CustomerResponseDTO} objects matching the search criteria.
     */
    @QueryHandler
    public List<CustomerResponseDTO> on(@NotNull SearchCustomersQuery query){
        log.info("SearchCustomersQuery handled");
        Page<Customer> customerPage = customerRepository
                .search("%" + query.getKeyword() + "%", PageRequest.of(query.getPage(), query.getSize()));
        List<CustomerResponseDTO> customers = mappers.fromListOfCustomers(customerPage.getContent());
        log.info(customers.size() + " customer(s) found");
        return customers;
    }

    /**
     * Handles the {@code GetAllCustomersQuery} by retrieving and mapping all customers.
     *
     * @param query The {@code GetAllCustomersQuery} to handle.
     * @return A list of {@link CustomerResponseDTO} objects representing all customers.
     */
    @QueryHandler
    public List<CustomerResponseDTO> on(@NotNull GetAllCustomersQuery query){
        log.info("GetAllCustomersQuery handled");
        List<Customer> customers = customerRepository.findAll();
        log.info("customer(s) found");
        return mappers.fromListOfCustomers(customers);
    }
}
