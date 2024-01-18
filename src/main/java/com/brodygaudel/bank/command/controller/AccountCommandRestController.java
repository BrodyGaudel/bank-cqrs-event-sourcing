package com.brodygaudel.bank.command.controller;

import com.brodygaudel.bank.common.util.IdGenerator;
import com.brodygaudel.bank.common.dto.*;
import com.brodygaudel.bank.common.command.account.*;
import com.brodygaudel.bank.common.enums.AccountStatus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * REST controller for handling account-related commands.
 *
 * <p>
 * This controller provides endpoints for creating, crediting, debiting, transferring, and updating the status of accounts.
 * It uses the Command Gateway to send corresponding commands to the system.
 * </p>
 */
@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandRestController {

    private final CommandGateway commandGateway;
    private final IdGenerator idGenerator;

    /**
     * Constructs a new instance of AccountCommandRestController.
     *
     * @param commandGateway The command gateway used to send commands to the system.
     * @param idGenerator    The ID generator used to generate unique IDs for account creation.
     */
    public AccountCommandRestController(CommandGateway commandGateway, IdGenerator idGenerator) {
        this.commandGateway = commandGateway;
        this.idGenerator = idGenerator;
    }

    /**
     * Endpoint for creating a new account.
     *
     * @param dto The data transfer object containing account information.
     * @return A CompletableFuture representing the completion of the command.
     */
    @PostMapping("/create")
    public CompletableFuture<String> create(@RequestBody @NotNull AccountRequestDTO dto) {
        return commandGateway.send(
                new CreateAccountCommand(
                        idGenerator.autoGenerate(),
                        BigDecimal.valueOf(0),
                        dto.status(),
                        LocalDateTime.now(),
                        dto.customerId()
                )
        );
    }

    /**
     * Endpoint for crediting an account.
     *
     * @param dto The data transfer object containing credit information.
     * @return A CompletableFuture representing the completion of the command.
     */
    @PostMapping("/credit")
    public CompletableFuture<String> credit(@RequestBody @NotNull CreditAccountRequestDTO dto) {
        return commandGateway.send(
                new CreditAccountCommand(
                        dto.id(),
                        dto.amount(),
                        dto.description(),
                        LocalDateTime.now()
                )
        );
    }

    /**
     * Endpoint for debiting an account.
     *
     * @param dto The data transfer object containing debit information.
     * @return A CompletableFuture representing the completion of the command.
     */
    @PostMapping("/debit")
    public CompletableFuture<String> debit(@RequestBody @NotNull DebitAccountRequestDTO dto) {
        return commandGateway.send(
                new DebitAccountCommand(
                        dto.id(),
                        dto.amount(),
                        dto.description(),
                        LocalDateTime.now()
                )
        );
    }

    /**
     * Endpoint for transferring funds between two accounts.
     *
     * @param dto The data transfer object containing transfer information.
     * @return A list of CompletableFutures representing the completion of debit and credit commands.
     */
    @PostMapping("/transfer")
    public List<CompletableFuture<String>> transfer(@RequestBody @NotNull TransferRequestDTO dto) {
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();

        String messageFrom = dto.description() + "| Transfer to :" + dto.idTo();
        CompletableFuture<String> debited = debit(new DebitAccountRequestDTO(
                dto.idFrom(), messageFrom, dto.amount()
        ));
        debited.join();
        completableFutures.add(debited);

        String messageTo = dto.description() + " | Transfer from :" + dto.idFrom();
        CompletableFuture<String> credited = credit(new CreditAccountRequestDTO(
                dto.idTo(), messageTo, dto.amount()
        ));
        credited.join();
        completableFutures.add(credited);

        return completableFutures;
    }

    /**
     * Endpoint for updating the status of an account.
     *
     * @param dto The data transfer object containing updated account status information.
     * @return A CompletableFuture representing the completion of the command.
     */
    @PostMapping("/update-status")
    public CompletableFuture<String> update(@RequestBody @NotNull AccountStatusUpdatedDTO dto) {
        if (dto.status().equals(AccountStatus.ACTIVATED)) {
            return commandGateway.send(new ActiveAccountCommand(dto.accountId(), dto.status(), LocalDateTime.now()));
        } else if (dto.status().equals(AccountStatus.SUSPENDED)) {
            return commandGateway.send(new SuspendAccountCommand(dto.accountId(), dto.status(), LocalDateTime.now()));
        } else {
            return null;
        }
    }
}
