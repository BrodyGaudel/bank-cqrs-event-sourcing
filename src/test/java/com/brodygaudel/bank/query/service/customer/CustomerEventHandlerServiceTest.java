package com.brodygaudel.bank.query.service.customer;

import com.brodygaudel.bank.common.event.customer.CustomerCreatedEvent;
import com.brodygaudel.bank.common.event.customer.CustomerDeletedEvent;
import com.brodygaudel.bank.common.event.customer.CustomerUpdatedEvent;
import com.brodygaudel.bank.query.entity.Customer;
import com.brodygaudel.bank.common.enums.Sex;
import com.brodygaudel.bank.query.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerEventHandlerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerEventHandlerService service;

    @BeforeEach
    void setUp() {
        service = new CustomerEventHandlerService(customerRepository);
    }

    @Test
    void onCustomerCreatedEvent() {
        CustomerCreatedEvent event = new CustomerCreatedEvent(
                "id", "nic", "john", "doe",
                "Gabon", LocalDate.now(), "Gabon",
                Sex.M, LocalDateTime.now()
        );
        Customer customer = Customer.builder()
                .id(event.getId())
                .nic(event.getNic())
                .firstname(event.getFirstname())
                .name(event.getName())
                .placeOfBirth(event.getPlaceOfBirth())
                .dateOfBirth(event.getDateOfBirth())
                .nationality(event.getNationality())
                .sex(event.getSex())
                .creation(event.getCreation())
                .lastUpdate(null)
                .build();
        when(customerRepository.save(any())).thenReturn(customer);
        service.on(event);
        verify(customerRepository, times(1)).save(any());
    }

    @Test
    void onCustomerUpdatedEvent() {
        CustomerUpdatedEvent event = new CustomerUpdatedEvent(
                "id", "nic", "john", "doe",
                "Gabon", LocalDate.now(), "Gabon",
                Sex.M, LocalDateTime.now()
        );
        Customer customer = Customer.builder()
                .id(event.getId())
                .nic(event.getNic())
                .firstname(event.getFirstname())
                .name(event.getName())
                .placeOfBirth(event.getPlaceOfBirth())
                .dateOfBirth(event.getDateOfBirth())
                .nationality(event.getNationality())
                .sex(event.getSex())
                .creation(LocalDateTime.now())
                .lastUpdate(event.getLastUpdate())
                .build();

        when(customerRepository.findById(event.getId())).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        service.on(event);
        verify(customerRepository, times(1)).findById(anyString());
        verify(customerRepository, times(1)).save(any());
    }

    @Test
    void onCustomerDeletedEvent() {
        CustomerDeletedEvent event = new CustomerDeletedEvent("id");
        service.on(event);
        verify(customerRepository, times(1)).deleteById(event.getId());
    }
}