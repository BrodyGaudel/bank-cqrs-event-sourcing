package com.brodygaudel.bank.common.event.customer;

import com.brodygaudel.bank.common.event.BaseEvent;
import com.brodygaudel.bank.query.enums.Sex;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CustomerUpdatedEvent extends BaseEvent<String> {

    private final String nic;
    private final String firstname;
    private final String name;
    private final String placeOfBirth;
    private final LocalDate dateOfBirth;
    private final String nationality;
    private final Sex sex;
    private final LocalDateTime lastUpdate;

    public CustomerUpdatedEvent(String id, String nic, String firstname, String name, String placeOfBirth, LocalDate dateOfBirth, String nationality, Sex sex, LocalDateTime lastUpdate) {
        super(id);
        this.nic = nic;
        this.firstname = firstname;
        this.name = name;
        this.placeOfBirth = placeOfBirth;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.sex = sex;
        this.lastUpdate = lastUpdate;
    }
}
