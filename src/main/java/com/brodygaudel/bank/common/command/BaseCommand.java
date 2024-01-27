package com.brodygaudel.bank.common.command;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Classe de base pour les commandes.
 *
 * @param <T> Le type de l'identifiant de l'agrégat cible.
 */
@Getter
public class BaseCommand<T> {
    @TargetAggregateIdentifier
    private final T id;

    public BaseCommand(T id) {
        this.id = id;
    }
}
