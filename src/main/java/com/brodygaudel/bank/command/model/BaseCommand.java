package com.brodygaudel.bank.command.model;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
public class BaseCommand<T> {
    @TargetAggregateIdentifier
    private T id;

    public BaseCommand(T id) {
        this.id = id;
    }
}
