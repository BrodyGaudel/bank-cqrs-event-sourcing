package com.brodygaudel.bank.command.controller;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

/**
 * REST controller for handling event store-related operations.
 *
 * <p>
 * This controller provides an endpoint for retrieving events stored in the event store for a specific aggregate ID.
 * It utilizes the Event Store to read and return the events as a Stream.
 * </p>
 */
@RestController
@RequestMapping("/events/store")
@Slf4j
public class EventStoreRestController {

    private final EventStore eventStore;

    /**
     * Constructs a new instance of EventStoreRestController.
     *
     * @param eventStore The Event Store used for retrieving stored events.
     */
    public EventStoreRestController(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    /**
     * Endpoint for retrieving events stored in the event store for a specific aggregate ID.
     *
     * @param id The unique identifier of the aggregate for which events are to be retrieved.
     * @return A Stream of DomainEventMessage representing the events stored for the specified aggregate ID.
     */
    @GetMapping("/get/{id}")
    public Stream getEventStored(@PathVariable String id) {
        log.info("In getEventStored()");
        Stream<? extends DomainEventMessage<?>> event = eventStore.readEvents(id).asStream();
        log.info("Events found");
        return event;
    }
}

