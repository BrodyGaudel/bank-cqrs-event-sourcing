package com.brodygaudel.bank.common.event.customer;

import com.brodygaudel.bank.common.event.BaseEvent;

public class CustomerDeletedEvent extends BaseEvent<String> {

    public CustomerDeletedEvent(String id) {
        super(id);
    }

}
