package com.brodygaudel.bank.command.model.customer;

import com.brodygaudel.bank.command.model.BaseCommand;

public class DeleteCustomerCommand extends BaseCommand<String> {
    public DeleteCustomerCommand(String id) {
        super(id);
    }
}
