package com.brodygaudel.bank.command.aggregate;

import com.brodygaudel.bank.common.event.account.*;
import com.brodygaudel.bank.common.exception.InsufficientBalanceException;
import com.brodygaudel.bank.common.command.account.*;
import com.brodygaudel.bank.common.enums.AccountStatus;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Aggregate representing an account in the system.
 *
 * <p>
 * This aggregate captures the state changes of an account, including creation, activation, suspension,
 * credit, and debit events. It applies the events and updates its internal state accordingly.
 * </p>
 */
@Aggregate
@Slf4j
@Getter
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private BigDecimal balance;
    private AccountStatus status;
    private LocalDateTime creation;
    private LocalDateTime lastUpdate;
    private String customerId;

    /**
     * Default constructor for the AccountAggregate.
     */
    public AccountAggregate() {
        super();
    }

    /**
     * Constructor for handling the {@code CreateAccountCommand} and applying the corresponding event.
     *
     * @param command The {@code CreateAccountCommand} to handle.
     */
    @CommandHandler
    public AccountAggregate(@NotNull CreateAccountCommand command) {
        log.info("CreateAccountCommand handled");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getBalance(),
                command.getStatus(),
                command.getCreation(),
                command.getCustomerId()
        ));
    }

    /**
     * Event sourcing handler for the {@code AccountCreatedEvent}.
     *
     * @param event The {@code AccountCreatedEvent} to handle.
     */
    @EventSourcingHandler
    public void on(@NotNull AccountCreatedEvent event) {
        log.info("AccountCreatedEvent handled");
        this.accountId = event.getId();
        this.balance = event.getBalance();
        this.status = event.getStatus();
        this.customerId = event.getCustomerId();
        this.creation = event.getCreation();
        AggregateLifecycle.apply(new AccountActivatedEvent(
                this.accountId,
                AccountStatus.ACTIVATED,
                LocalDateTime.now()
        ));
    }

    /**
     * Command handler for handling the {@code DebitAccountCommand} and applying the corresponding event.
     *
     * @param command The {@code DebitAccountCommand} to handle.
     */
    @CommandHandler
    public void on(DebitAccountCommand command) {
        log.info("DebitAccountCommand handled");
        if (this.balance.compareTo(BigDecimal.ZERO) > 0 && this.balance.compareTo(command.getAmount()) < 0) {
            throw new InsufficientBalanceException("Balance not sufficient => " + this.balance);
        } else {
            AggregateLifecycle.apply(new AccountDebitedEvent(
                    command.getId(),
                    command.getAmount(),
                    command.getDescription(),
                    command.getLastUpdate()
            ));
        }
    }

    /**
     * Event sourcing handler for the {@code AccountDebitedEvent}.
     *
     * @param event The {@code AccountDebitedEvent} to handle.
     */
    @EventSourcingHandler
    public void on(@NotNull AccountDebitedEvent event) {
        log.info("AccountDebitedEvent handled");
        this.accountId = event.getId();
        this.balance = this.balance.subtract(event.getAmount());
        this.lastUpdate = event.getLastUpdate();
    }

    /**
     * Command handler for handling the {@code CreditAccountCommand} and applying the corresponding event.
     *
     * @param command The {@code CreditAccountCommand} to handle.
     */
    @CommandHandler
    public void on(@NotNull CreditAccountCommand command) {
        log.info("CreditAccountCommand handled");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getDescription(),
                command.getLastUpdate()
        ));
    }

    /**
     * Event sourcing handler for the {@code AccountCreditedEvent}.
     *
     * @param event The {@code AccountCreditedEvent} to handle.
     */
    @EventSourcingHandler
    public void on(@NotNull AccountCreditedEvent event) {
        log.info("AccountCreditedEvent handled");
        this.accountId = event.getId();
        this.lastUpdate = event.getLastUpdate();
        this.balance = this.balance.add(event.getAmount());
    }

    /**
     * Command handler for handling the {@code ActiveAccountCommand} and applying the corresponding event.
     *
     * @param command The {@code ActiveAccountCommand} to handle.
     */
    @CommandHandler
    public void on(@NotNull ActiveAccountCommand command) {
        log.info("ActiveAccountCommand handled");
        AggregateLifecycle.apply(new AccountActivatedEvent(
                command.getId(),
                command.getStatus(),
                command.getLastUpdate()
        ));
    }

    /**
     * Event sourcing handler for the {@code AccountActivatedEvent}.
     *
     * @param event The {@code AccountActivatedEvent} to handle.
     */
    @EventSourcingHandler
    public void on(@NotNull AccountActivatedEvent event) {
        log.info("AccountActivatedEvent handled");
        this.accountId = event.getId();
        this.status = event.getStatus();
        this.lastUpdate = event.getLastUpdate();
    }

    /**
     * Command handler for handling the {@code SuspendAccountCommand} and applying the corresponding event.
     *
     * @param command The {@code SuspendAccountCommand} to handle.
     */
    @CommandHandler
    public void on(@NotNull SuspendAccountCommand command) {
        log.info("SuspendAccountCommand handled");
        AggregateLifecycle.apply(new AccountSuspendedEvent(
                command.getId(),
                command.getStatus(),
                command.getLastUpdate()
        ));
    }

    /**
     * Event sourcing handler for the {@code AccountSuspendedEvent}.
     *
     * @param event The {@code AccountSuspendedEvent} to handle.
     */
    @EventSourcingHandler
    public void on(@NotNull AccountSuspendedEvent event) {
        log.info("AccountSuspendedEvent handled");
        this.accountId = event.getId();
        this.status = event.getStatus();
        this.lastUpdate = event.getLastUpdate();
    }
}
