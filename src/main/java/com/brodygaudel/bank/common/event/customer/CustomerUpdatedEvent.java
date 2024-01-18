package com.brodygaudel.bank.common.event.customer;

import com.brodygaudel.bank.common.event.BaseEvent;
import com.brodygaudel.bank.common.enums.Sex;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Event representing the update of customer information in the system.
 *
 * <p>
 * This event is triggered when existing customer details are successfully updated. It includes details such as the
 * customer's unique identifier, updated personal information, and the timestamp of the last update.
 * </p>
 * @see BaseEvent
 */
@Getter
public class CustomerUpdatedEvent extends BaseEvent<String> {

    /**
     * The National Identity Card (NIC) of the customer after the update.
     */
    private final String nic;

    /**
     * The updated first name of the customer.
     */
    private final String firstname;

    /**
     * The updated last name of the customer.
     */
    private final String name;

    /**
     * The updated place of birth of the customer.
     */
    private final String placeOfBirth;

    /**
     * The updated date of birth of the customer.
     */
    private final LocalDate dateOfBirth;

    /**
     * The updated nationality of the customer.
     */
    private final String nationality;

    /**
     * The updated gender/sex of the customer.
     */
    private final Sex sex;

    /**
     * The timestamp representing the last update of the customer information.
     */
    private final LocalDateTime lastUpdate;

    /**
     * Constructs a new CustomerUpdatedEvent with the specified updated details.
     *
     * @param id          The unique identifier associated with the event.
     * @param nic         The National Identity Card (NIC) of the customer after the update.
     * @param firstname   The updated first name of the customer.
     * @param name        The updated last name of the customer.
     * @param placeOfBirth The updated place of birth of the customer.
     * @param dateOfBirth The updated date of birth of the customer.
     * @param nationality The updated nationality of the customer.
     * @param sex         The updated gender/sex of the customer.
     * @param lastUpdate  The timestamp representing the last update of the customer information.
     */
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
