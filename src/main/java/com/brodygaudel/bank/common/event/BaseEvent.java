package com.brodygaudel.bank.common.event;

import lombok.Getter;

/**
 * Base class for event objects in the application.
 *
 * <p>
 * This class serves as the base class for all event objects. It contains a unique identifier {@code id} associated with
 * the event. Subclasses can extend this class to include additional information specific to the events they represent.
 * </p>
 *
 * @param <T> The type of the identifier associated with the event.
 */
@Getter
public class BaseEvent<T> {

    /**
     * The unique identifier associated with the event.
     */
    private final T id;

    /**
     * Constructs a new BaseEvent with the specified identifier.
     *
     * @param id The unique identifier associated with the event.
     */
    public BaseEvent(T id) {
        this.id = id;
    }
}

