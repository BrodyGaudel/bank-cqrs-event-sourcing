package com.brodygaudel.bank.command.controller;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/events/store")
@Slf4j
public class EventStoreRestController {

    private final EventStore eventStore;

    public EventStoreRestController(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @GetMapping("/get/{id}")
    public Stream getEventStored(@PathVariable String id){
        log.info("In getEventStored()");
        Stream<? extends DomainEventMessage<?>> event = eventStore.readEvents(id).asStream();
        log.info("event found");
        return  event;
    }

}
