package com.brodygaudel.bank.command.controller;

import com.brodygaudel.bank.command.dto.*;
import com.brodygaudel.bank.command.model.account.*;
import com.brodygaudel.bank.command.util.IdGenerator;
import com.brodygaudel.bank.query.enums.AccountStatus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandRestController {

    private final CommandGateway commandGateway;
    private final IdGenerator idGenerator;

    public AccountCommandRestController(CommandGateway commandGateway, IdGenerator idGenerator) {
        this.commandGateway = commandGateway;
        this.idGenerator = idGenerator;
    }

    @PostMapping("/create")
    public CompletableFuture<String> create(@RequestBody @NotNull AccountRequestDTO dto){
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

    @PostMapping("/credit")
    public CompletableFuture<String> credit(@RequestBody @NotNull CreditAccountRequestDTO dto){
        return commandGateway.send(
                new CreditAccountCommand(
                        dto.id(),
                        dto.amount(),
                        dto.description(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debit(@RequestBody @NotNull DebitAccountRequestDTO dto){
        return commandGateway.send(
                new DebitAccountCommand(
                        dto.id(),
                        dto.amount(),
                        dto.description(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/transfer")
    public List<CompletableFuture<String>> transfer(@RequestBody @NotNull TransferRequestDTO dto){
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();

        String messageFrom = dto.description()+"| Transfer to :"+dto.idTo();
        CompletableFuture<String>  debited =  debit( new DebitAccountRequestDTO(
                dto.idFrom(), messageFrom, dto.amount()
        ));
        debited.join();
        completableFutures.add(debited);

        String messageTo = dto.description()+" | Transfer from :"+dto.idFrom();
        CompletableFuture<String> credited = credit( new CreditAccountRequestDTO(
                dto.idTo(), messageTo, dto.amount()
        ));
        credited.join();
        completableFutures.add(credited);

        return completableFutures;
    }

    @PostMapping("/update-status")
    public CompletableFuture<String> update(@RequestBody @NotNull AccountStatusUpdatedDTO dto){
        if(dto.status().equals(AccountStatus.ACTIVATED)){
            return commandGateway.send(new ActiveAccountCommand(dto.accountId(), dto.status(), LocalDateTime.now()));
        } else if (dto.status().equals(AccountStatus.SUSPENDED)) {
            return commandGateway.send(new SuspendAccountCommand(dto.accountId(), dto.status(), LocalDateTime.now()));
        } else {
            return null;
        }
    }
}
