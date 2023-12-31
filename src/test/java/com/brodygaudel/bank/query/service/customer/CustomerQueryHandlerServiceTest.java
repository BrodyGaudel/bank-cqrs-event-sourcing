package com.brodygaudel.bank.query.service.customer;

import com.brodygaudel.bank.query.dto.CustomerResponseDTO;
import com.brodygaudel.bank.query.entity.Customer;
import com.brodygaudel.bank.query.enums.Sex;
import com.brodygaudel.bank.query.model.GetAllCustomersQuery;
import com.brodygaudel.bank.query.model.GetCustomerByIdQuery;
import com.brodygaudel.bank.query.model.SearchCustomersQuery;
import com.brodygaudel.bank.query.repository.CustomerRepository;
import com.brodygaudel.bank.query.util.Mappers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerQueryHandlerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private Mappers mappers;

    @InjectMocks
    private CustomerQueryHandlerService service;

    @BeforeEach
    void setUp() {
        service = new CustomerQueryHandlerService(customerRepository, mappers);
    }

    @Test
    void onGetCustomerByIdQuery() {
        GetCustomerByIdQuery query = new GetCustomerByIdQuery("ID");

        Customer customer = Customer.builder().nic("NIC").sex(Sex.M).name("Doe").firstname("John")
                .nationality("Gabon").id(query.getId()).creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now())
                .build();
        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder().nic(customer.getNic()).sex(Sex.M).name(customer.getName()).firstname(customer.getFirstname())
                .nationality(customer.getNationality()).id(query.getId()).creation(customer.getCreation()).lastUpdate(customer.getLastUpdate())
                .build();

        when(customerRepository.findById(query.getId())).thenReturn(Optional.of(customer));
        when(mappers.fromCustomer(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO response = service.on(query);
        assertNotNull(response);
        assertEquals(response.getId(), query.getId());
    }

    @Test
    void onSearchCustomersQuery() {

        SearchCustomersQuery query = new SearchCustomersQuery("John", 0, 2);

        Customer customer1 = Customer.builder().nic("NIC").sex(Sex.M).name("Doe").firstname("John").nationality("Gabon")
                .id("ID1").creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now()).build();
        Customer customer2 = Customer.builder().nic("NIC").sex(Sex.M).name("Smith").firstname("John").nationality("Gabon")
                .id("ID2").creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now()).build();
        CustomerResponseDTO dto1 = CustomerResponseDTO.builder().nic(customer1.getNic()).sex(Sex.M).name(customer1.getName())
                .firstname(customer1.getFirstname()).nationality(customer1.getNationality()).id("ID1").creation(customer1.getCreation())
                .lastUpdate(customer1.getLastUpdate()).build();
        CustomerResponseDTO dto2 = CustomerResponseDTO.builder().nic(customer2.getNic()).sex(Sex.M).name(customer2.getName())
                .firstname(customer2.getFirstname()).nationality(customer2.getNationality()).id("ID2").creation(customer2.getCreation())
                .lastUpdate(customer2.getLastUpdate()).build();


        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(customerRepository.search("%"+query.getKeyword()+"%", PageRequest.of(query.getPage(), query.getSize()))).thenReturn(
                new PageImpl<>(customers, PageRequest.of(query.getPage(), query.getSize()), 2)
        );
        when(mappers.fromListOfCustomers(customers)).thenReturn(List.of(dto2, dto1));

        List<CustomerResponseDTO> response = service.on(query);
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
    }

    @Test
    void onGetAllCustomersQuery() {
        GetAllCustomersQuery query = new GetAllCustomersQuery();

        Customer customer1 = Customer.builder().nic("NIC").sex(Sex.M).name("Doe").firstname("John").nationality("Gabon")
                .id("ID1").creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now()).build();
        Customer customer2 = Customer.builder().nic("NIC").sex(Sex.M).name("Smith").firstname("John").nationality("Gabon")
                .id("ID2").creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now()).build();
        CustomerResponseDTO dto1 = CustomerResponseDTO.builder().nic(customer1.getNic()).sex(Sex.M).name(customer1.getName())
                .firstname(customer1.getFirstname()).nationality(customer1.getNationality()).id("ID1").creation(customer1.getCreation())
                .lastUpdate(customer1.getLastUpdate()).build();
        CustomerResponseDTO dto2 = CustomerResponseDTO.builder().nic(customer2.getNic()).sex(Sex.M).name(customer2.getName())
                .firstname(customer2.getFirstname()).nationality(customer2.getNationality()).id("ID2").creation(customer2.getCreation())
                .lastUpdate(customer2.getLastUpdate()).build();

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(customerRepository.findAll()).thenReturn(customers);
        when(mappers.fromListOfCustomers(customers)).thenReturn(List.of(dto2, dto1));

        List<CustomerResponseDTO> response = service.on(query);
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
    }
}