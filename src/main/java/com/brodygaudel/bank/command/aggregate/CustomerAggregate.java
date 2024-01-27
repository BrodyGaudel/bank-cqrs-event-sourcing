package com.brodygaudel.bank.command.aggregate;

import com.brodygaudel.bank.common.command.customer.CreateCustomerCommand;
import com.brodygaudel.bank.common.command.customer.DeleteCustomerCommand;
import com.brodygaudel.bank.common.command.customer.UpdateCustomerCommand;
import com.brodygaudel.bank.common.event.customer.CustomerCreatedEvent;
import com.brodygaudel.bank.common.event.customer.CustomerDeletedEvent;
import com.brodygaudel.bank.common.event.customer.CustomerUpdatedEvent;
import com.brodygaudel.bank.common.enums.Sex;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Aggregate representing a customer in the system.
 *
 * <p>
 * This aggregate captures the state changes of a customer, including creation, update, and deletion events.
 * It applies the events and updates its internal state accordingly.
 * </p>
 */
@Aggregate
@Slf4j
@Getter
public class CustomerAggregate {

    @AggregateIdentifier
    private String customerId;
    private String nic;
    private String firstname;
    private String name;
    private String placeOfBirth;
    private LocalDate dateOfBirth;
    private String nationality;
    private Sex sex;
    private LocalDateTime creation;
    private LocalDateTime lastUpdate;

    /**
     * Default constructor for the CustomerAggregate.
     */
    public CustomerAggregate() {
        super();
    }

    /**
     * Constructor for handling the {@link CreateCustomerCommand} and applying the corresponding event.
     *
     * @param command The {@link CreateCustomerCommand} to handle.
     */
    @CommandHandler
    public CustomerAggregate(@NotNull CreateCustomerCommand command) {
        log.info("CreateCustomerCommand received");
        AggregateLifecycle.apply(new CustomerCreatedEvent(
                command.getId(),
                command.getNic(),
                command.getFirstname(),
                command.getName(),
                command.getPlaceOfBirth(),
                command.getDateOfBirth(),
                command.getNationality(),
                command.getSex(),
                command.getCreation()
        ));
    }

    /**
     * Event sourcing handler for the {@link CustomerCreatedEvent}.
     *
     * @param event The {@link CustomerCreatedEvent} to handle.
     */
    @EventSourcingHandler
    public void on(@NotNull CustomerCreatedEvent event) {
        log.info("CustomerCreatedEvent sourced");
        this.customerId = event.getId();
        this.nic = event.getNic();
        this.firstname = event.getFirstname();
        this.name = event.getName();
        this.placeOfBirth = event.getPlaceOfBirth();
        this.dateOfBirth = event.getDateOfBirth();
        this.nationality = event.getNationality();
        this.sex = event.getSex();
        this.creation = event.getCreation();
    }

    /**
     * Command handler for handling the {@link UpdateCustomerCommand} and applying the corresponding event.
     *
     * @param command The {@link UpdateCustomerCommand} to handle.
     */
    @CommandHandler
    public void handle(@NotNull UpdateCustomerCommand command) {
        log.info("UpdateCustomerCommand received");
        AggregateLifecycle.apply(new CustomerUpdatedEvent(
                command.getId(), command.getNic(),
                command.getFirstname(), command.getName(),
                command.getPlaceOfBirth(), command.getDateOfBirth(),
                command.getNationality(), command.getSex(),
                command.getLastUpdate()
        ));
    }

    /**
     * Event sourcing handler for the {@link CustomerUpdatedEvent}.
     *
     * @param event The {@link CustomerUpdatedEvent} to handle.
     */
    @EventSourcingHandler
    public void on(@NotNull CustomerUpdatedEvent event) {
        log.info("CustomerUpdatedEvent sourced");
        this.customerId = event.getId();
        this.nic = event.getNic();
        this.firstname = event.getFirstname();
        this.name = event.getName();
        this.lastUpdate = event.getLastUpdate();
        this.placeOfBirth = event.getPlaceOfBirth();
        this.dateOfBirth = event.getDateOfBirth();
        this.nationality = event.getNationality();
        this.sex = event.getSex();
    }

    /**
     * Command handler for handling the {@link DeleteCustomerCommand} and applying the corresponding event.
     *
     * @param command The {@link DeleteCustomerCommand} to handle.
     */
    @CommandHandler
    public void handle(@NotNull DeleteCustomerCommand command) {
        log.info("DeleteCustomerCommand received");
        AggregateLifecycle.apply(new CustomerDeletedEvent(command.getId()));
    }

    /**
     * Event sourcing handler for the {@link CustomerDeletedEvent}.
     *
     * @param event The {@link CustomerDeletedEvent} to handle.
     */
    @EventSourcingHandler
    public void on(@NotNull CustomerDeletedEvent event) {
        log.info("CustomerDeletedEvent sourced");
        this.customerId = event.getId();
    }
}
