package com.brodygaudel.bank.command.controller;

import com.brodygaudel.bank.command.dto.CustomerRequestDTO;
import com.brodygaudel.bank.command.model.customer.CreateCustomerCommand;
import com.brodygaudel.bank.command.model.customer.DeleteCustomerCommand;
import com.brodygaudel.bank.command.model.customer.UpdateCustomerCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/customers")
public class CustomerCommandRestController {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public CustomerCommandRestController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public CompletableFuture<String> create(@RequestBody @NotNull CustomerRequestDTO dto){
        return commandGateway.send( new CreateCustomerCommand(
                UUID.randomUUID().toString(), dto.nic(),
                dto.firstname(), dto.name(), dto.placeOfBirth(),
                dto.dateOfBirth(), dto.nationality(), dto.sex(),
                LocalDateTime.now()
        ));
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<String> update(@PathVariable String id, @RequestBody @NotNull CustomerRequestDTO dto){
        return commandGateway.send( new UpdateCustomerCommand(
                id, dto.nic(), dto.firstname(), dto.name(),
                dto.placeOfBirth(), dto.dateOfBirth(),
                dto.nationality(), dto.sex(),
                LocalDateTime.now()
        ));
    }

    @DeleteMapping("/delete/{id}")
    public CompletableFuture<String> delete(@PathVariable String id){
        return commandGateway.send( new DeleteCustomerCommand(id));
    }

    @GetMapping("/event-store/{id}")
    public Stream eventStore(@PathVariable String id){
        return eventStore.readEvents(id).asStream();
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
