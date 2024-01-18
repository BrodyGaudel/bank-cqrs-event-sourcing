package com.brodygaudel.bank.common.command;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Base class for representing commands with a target aggregate identifier.
 *
 * <p>
 * This class provides a generic structure for commands with a target aggregate identifier. It includes a getter method
 * for retrieving the identifier and a constructor to initialize the identifier during command creation.
 * </p>
 *
 * @param <T> The type of the target aggregate identifier.
 */
@Getter
public class BaseCommand<T> {

    /**
     * The target aggregate identifier.
     */
    @TargetAggregateIdentifier
    private final T id;

    /**
     * Constructs a new instance of BaseCommand with the specified target aggregate identifier.
     *
     * @param id The target aggregate identifier for the command.
     */
    public BaseCommand(T id) {
        this.id = id;
    }
}

