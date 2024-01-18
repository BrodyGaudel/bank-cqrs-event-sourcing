package com.brodygaudel.bank.command.controller;

import com.brodygaudel.bank.common.dto.CustomerRequestDTO;
import com.brodygaudel.bank.common.command.customer.CreateCustomerCommand;
import com.brodygaudel.bank.common.command.customer.DeleteCustomerCommand;
import com.brodygaudel.bank.common.command.customer.UpdateCustomerCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * REST controller for handling customer-related commands.
 *
 * <p>
 * This controller provides endpoints for creating, updating, and deleting customers.
 * It uses the Command Gateway to send corresponding commands to the system.
 * </p>
 */
@RestController
@RequestMapping("/commands/customers")
public class CustomerCommandRestController {

    private final CommandGateway commandGateway;

    /**
     * Constructs a new instance of CustomerCommandRestController.
     *
     * @param commandGateway The command gateway used to send commands to the system.
     */
    public CustomerCommandRestController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    /**
     * Endpoint for creating a new customer.
     *
     * @param dto The data transfer object containing customer information.
     * @return A CompletableFuture representing the completion of the command.
     */
    @PostMapping("/create")
    public CompletableFuture<String> create(@RequestBody @NotNull CustomerRequestDTO dto) {
        return commandGateway.send(new CreateCustomerCommand(
                UUID.randomUUID().toString(), dto.nic(),
                dto.firstname(), dto.name(), dto.placeOfBirth(),
                dto.dateOfBirth(), dto.nationality(), dto.sex(),
                LocalDateTime.now()
        ));
    }

    /**
     * Endpoint for updating an existing customer.
     *
     * @param id  The unique identifier of the customer to update.
     * @param dto The data transfer object containing updated customer information.
     * @return A CompletableFuture representing the completion of the command.
     */
    @PutMapping("/update/{id}")
    public CompletableFuture<String> update(@PathVariable String id, @RequestBody @NotNull CustomerRequestDTO dto) {
        return commandGateway.send(new UpdateCustomerCommand(
                id, dto.nic(), dto.firstname(), dto.name(),
                dto.placeOfBirth(), dto.dateOfBirth(),
                dto.nationality(), dto.sex(),
                LocalDateTime.now()
        ));
    }

    /**
     * Endpoint for deleting a customer.
     *
     * @param id The unique identifier of the customer to delete.
     * @return A CompletableFuture representing the completion of the command.
     */
    @DeleteMapping("/delete/{id}")
    public CompletableFuture<String> delete(@PathVariable String id) {
        return commandGateway.send(new DeleteCustomerCommand(id));
    }

    /**
     * Exception handler for handling exceptions thrown within this controller.
     *
     * @param exception The exception to handle.
     * @return A ResponseEntity with an error message and HTTP status code.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
