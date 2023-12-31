package com.brodygaudel.bank.command.aggregate;

import com.brodygaudel.bank.command.model.customer.CreateCustomerCommand;
import com.brodygaudel.bank.command.model.customer.DeleteCustomerCommand;
import com.brodygaudel.bank.command.model.customer.UpdateCustomerCommand;
import com.brodygaudel.bank.common.event.customer.CustomerCreatedEvent;
import com.brodygaudel.bank.common.event.customer.CustomerDeletedEvent;
import com.brodygaudel.bank.common.event.customer.CustomerUpdatedEvent;
import com.brodygaudel.bank.query.enums.Sex;
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

    public CustomerAggregate() {
        super();
    }

    @CommandHandler
    public CustomerAggregate(@NotNull CreateCustomerCommand command) {
        log.info("CreateCustomerCommand received");
        AggregateLifecycle.apply( new CustomerCreatedEvent(
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

    @EventSourcingHandler
    public void on(@NotNull CustomerCreatedEvent event){
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

    @CommandHandler
    public void handle(@NotNull UpdateCustomerCommand command){
        log.info("UpdateCustomerCommand received");
        AggregateLifecycle.apply( new CustomerUpdatedEvent(
                command.getId(), command.getNic(),
                command.getFirstname(), command.getName(),
                command.getPlaceOfBirth(), command.getDateOfBirth(),
                command.getNationality(), command.getSex(),
                command.getLastUpdate()
        ));
    }

    @EventSourcingHandler
    public void on(@NotNull CustomerUpdatedEvent event){
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

    @CommandHandler
    public void handle(@NotNull DeleteCustomerCommand command){
        log.info("DeleteCustomerCommand received");
        AggregateLifecycle.apply( new CustomerDeletedEvent(command.getId()));
    }

    @EventSourcingHandler
    public void on(@NotNull CustomerDeletedEvent event){
        log.info("CustomerDeletedEvent sourced");
        //this.customerId = event.getId();
    }

}
