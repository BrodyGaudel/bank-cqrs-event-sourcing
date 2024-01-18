package com.brodygaudel.bank.common.command.customer;

import com.brodygaudel.bank.common.command.BaseCommand;

/**
 * Command to delete a customer.
 *
 * <p>
 * This command is used to initiate the deletion of a customer. It includes the unique identifier
 * of the customer inherited from the BaseCommand class.
 * </p>
 *
 * @see BaseCommand
 */
public class DeleteCustomerCommand extends BaseCommand<String> {

    /**
     * Constructs a new instance of DeleteCustomerCommand with the specified unique identifier.
     *
     * @param id The unique identifier for the customer to be deleted.
     */
    public DeleteCustomerCommand(String id) {
        super(id);
    }
}

