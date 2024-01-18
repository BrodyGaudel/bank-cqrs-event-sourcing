package com.brodygaudel.bank.common.command.customer;

import com.brodygaudel.bank.common.command.BaseCommand;
import com.brodygaudel.bank.common.enums.Sex;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Command to update an existing customer.
 *
 * <p>
 * This command is used to initiate the update of an existing customer. It includes all the necessary information
 * such as the National Identity Card (NIC), first name, last name, place of birth, date of birth, nationality, sex,
 * and the timestamp of the last update. The unique identifier for the customer is inherited from the BaseCommand class.
 * </p>
 *
 * @see BaseCommand
 */
@Getter
public class UpdateCustomerCommand extends BaseCommand<String> {

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
     * The sex of the customer.
     */
    private final Sex sex;

    /**
     * The timestamp indicating when the customer was last updated.
     */
    private final LocalDateTime lastUpdate;

    /**
     * Constructs a new instance of UpdateCustomerCommand with the specified parameters.
     *
     * @param id         The unique identifier for the customer.
     * @param nic        The National Identity Card (NIC) of the customer.
     * @param firstname  The first name of the customer.
     * @param name       The last name of the customer.
     * @param placeOfBirth The place of birth of the customer.
     * @param dateOfBirth The date of birth of the customer.
     * @param nationality The nationality of the customer.
     * @param sex        The sex of the customer.
     * @param lastUpdate The timestamp indicating when the customer was last updated.
     */
    public UpdateCustomerCommand(String id, String nic, String firstname, String name, String placeOfBirth,
                                 LocalDate dateOfBirth, String nationality, Sex sex, LocalDateTime lastUpdate) {
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
