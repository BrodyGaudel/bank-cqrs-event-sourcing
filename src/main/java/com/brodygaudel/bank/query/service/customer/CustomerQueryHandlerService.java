package com.brodygaudel.bank.query.service.customer;

import com.brodygaudel.bank.query.dto.CustomerResponseDTO;
import com.brodygaudel.bank.query.entity.Customer;
import com.brodygaudel.bank.common.exception.CustomerNotFoundException;
import com.brodygaudel.bank.query.util.Mappers;
import com.brodygaudel.bank.query.model.GetAllCustomersQuery;
import com.brodygaudel.bank.query.model.GetCustomerByIdQuery;
import com.brodygaudel.bank.query.model.SearchCustomersQuery;
import com.brodygaudel.bank.query.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerQueryHandlerService {

    private final CustomerRepository customerRepository;
    private final Mappers mappers;


    public CustomerQueryHandlerService(CustomerRepository customerRepository, Mappers mappers) {
        this.customerRepository = customerRepository;
        this.mappers = mappers;
    }

    @QueryHandler
    public CustomerResponseDTO on(@NotNull GetCustomerByIdQuery query){
        log.info("GetCustomerByIdQuery handled");
        Customer customer = customerRepository.findById(query.getId())
                .orElseThrow( () -> new CustomerNotFoundException("customer with id '"+query.getId()+"' not found."));
        log.info("customer found");
        return mappers.fromCustomer(customer);
    }

    @QueryHandler
    public List<CustomerResponseDTO> on(@NotNull SearchCustomersQuery query){
        log.info("SearchCustomersQuery handled");
        Page<Customer> customerPage = customerRepository
                .search("%"+query.getKeyword()+"%", PageRequest.of(query.getPage(), query.getSize()));
        List<CustomerResponseDTO> customers = mappers.fromListOfCustomers(customerPage.getContent());
        log.info(customers.size()+" customer(s) found");
        return customers;
    }

    @QueryHandler
    public List<CustomerResponseDTO> on(@NotNull GetAllCustomersQuery query){
        log.info("GetAllCustomersQuery handled");
        List<Customer> customers = customerRepository.findAll();
        log.info("customer(s) found");
        return mappers.fromListOfCustomers(customers);
    }
}
