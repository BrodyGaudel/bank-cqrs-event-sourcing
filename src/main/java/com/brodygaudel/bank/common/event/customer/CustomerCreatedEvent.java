package com.brodygaudel.bank.common.event.customer;

import com.brodygaudel.bank.common.event.BaseEvent;
import com.brodygaudel.bank.common.enums.Sex;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Event representing the creation of a customer in the system.
 *
 * <p>
 * This event is triggered when a new customer is successfully created. It includes details such as the customer's
 * unique identifier, personal information, and creation timestamp.
 * </p>
 * @see BaseEvent
 */
@Getter
public class CustomerCreatedEvent extends BaseEvent<String> {

    /**
     * The National Identity Card (NIC) of the customer.
     */
    private final String nic;

    /**
     * The first name of the customer.
     */
    private final String firstname;

    /**
     * The last name of the customer.
     */
    private final String name;

    /**
     * The place of birth of the customer.
     */
    private final String placeOfBirth;

    /**
     * The date of birth of the customer.
     */
    private final LocalDate dateOfBirth;

    /**
     * The nationality of the customer.
     */
    private final String nationality;

    /**
     * The gender/sex of the customer.
     */
    private final Sex sex;

    /**
     * The timestamp representing the creation of the customer.
     */
    private final LocalDateTime creation;

    /**
     * Constructs a new CustomerCreatedEvent with the specified details.
     *
     * @param id        The unique identifier associated with the event.
     * @param nic       The National Identity Card (NIC) of the customer.
     * @param firstname The first name of the customer.
     * @param name      The last name of the customer.
     * @param placeOfBirth The place of birth of the customer.
     * @param dateOfBirth The date of birth of the customer.
     * @param nationality The nationality of the customer.
     * @param sex        The gender/sex of the customer.
     * @param creation  The timestamp representing the creation of the customer.
     */
    public CustomerCreatedEvent(String id, String nic, String firstname, String name, String placeOfBirth, LocalDate dateOfBirth, String nationality, Sex sex, LocalDateTime creation) {
        super(id);
        this.nic = nic;
        this.firstname = firstname;
        this.name = name;
        this.placeOfBirth = placeOfBirth;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.sex = sex;
        this.creation = creation;
    }
}
