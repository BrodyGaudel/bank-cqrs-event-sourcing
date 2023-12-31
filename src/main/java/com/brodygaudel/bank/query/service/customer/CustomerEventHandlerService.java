package com.brodygaudel.bank.query.service.customer;

import com.brodygaudel.bank.common.event.customer.CustomerCreatedEvent;
import com.brodygaudel.bank.common.event.customer.CustomerDeletedEvent;
import com.brodygaudel.bank.common.event.customer.CustomerUpdatedEvent;
import com.brodygaudel.bank.query.entity.Customer;
import com.brodygaudel.bank.common.exception.CustomerNotFoundException;
import com.brodygaudel.bank.common.exception.NicAlreadyExistException;
import com.brodygaudel.bank.query.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class CustomerEventHandlerService {

    private final CustomerRepository customerRepository;

    public CustomerEventHandlerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @EventHandler
    public void on(@NotNull CustomerCreatedEvent event){
        log.info("CustomerCreatedEvent handled");
        checkIfNicAlreadyExist(event.getNic());
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
        Customer customerCreated = customerRepository.save(customer);
        log.info("customer created at :"+customerCreated.getCreation());
    }

    @EventHandler
    public void on(@NotNull CustomerUpdatedEvent event) {
        log.info("CustomerUpdatedEvent handled");
        Customer customer = getCustomerById(event.getId());
        if(!customer.getNic().equals(event.getNic())){
            checkIfNicAlreadyExist(event.getNic());
        }
        setNewItemsValue(customer, event);
        Customer customerUpdated = customerRepository.save(customer);
        log.info("customer updated at :"+customerUpdated.getLastUpdate());
    }

    @EventHandler
    public void on(@NotNull CustomerDeletedEvent event) {
        log.info("CustomerDeletedEvent handled");
        customerRepository.deleteById(event.getId());
        log.info("customer deleted");
    }


    /**
     * Checks if a National Identity Card (NIC) already exists in the customer repository.
     *
     * @param nic The National Identity Card (NIC) to check for existence.
     * @throws NicAlreadyExistException if a customer with the given NIC already exists.
     */
    private void checkIfNicAlreadyExist(String nic){
        Boolean exist = customerRepository.checkIfNicExists(nic);
        if(Boolean.TRUE.equals(exist)){
            throw new NicAlreadyExistException("national identity card '"+nic+"' already exist");
        }
    }

    /**
     * Retrieves a {@link Customer} entity by its unique identifier.
     *
     * @param id The unique identifier of the customer to retrieve.
     * @return The {@link Customer} instance with the specified ID.
     * @throws CustomerNotFoundException if no customer with the given ID is found.
     */
    private Customer getCustomerById(String id){
        return customerRepository.findById(id)
                .orElseThrow( () -> new CustomerNotFoundException("customer with id '"+id+"' not found."));
    }

    /**
     * Sets the new values from a {@link CustomerUpdatedEvent} onto a {@link Customer} entity.
     *
     * @param customer The {@link Customer} entity to update with new values.
     * @param event    The {@link CustomerUpdatedEvent} containing the updated information.
     */
    private void setNewItemsValue(@NotNull Customer customer, @NotNull CustomerUpdatedEvent event){
        customer.setNic(event.getNic());
        customer.setFirstname(event.getFirstname());
        customer.setName(event.getName());
        customer.setPlaceOfBirth(event.getPlaceOfBirth());
        customer.setDateOfBirth(event.getDateOfBirth());
        customer.setNationality(event.getNationality());
        customer.setSex(event.getSex());
        customer.setLastUpdate(event.getLastUpdate());
    }
}
