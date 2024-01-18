package com.brodygaudel.bank.common.event.customer;

import com.brodygaudel.bank.common.event.BaseEvent;

/**
 * Event representing the deletion of a customer in the system.
 *
 * <p>
 * This event is triggered when a customer is successfully deleted from the system. It includes the unique identifier
 * of the customer whose information has been deleted.
 * </p>
 *
 * @see BaseEvent
 */
public class CustomerDeletedEvent extends BaseEvent<String> {

    /**
     * Constructs a new CustomerDeletedEvent with the specified unique identifier.
     *
     * @param id The unique identifier associated with the event, representing the customer whose information has been deleted.
     */
    public CustomerDeletedEvent(String id) {
        super(id);
    }
}

