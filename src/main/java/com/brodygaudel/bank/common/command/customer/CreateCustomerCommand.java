package com.brodygaudel.bank.common.command.customer;

import com.brodygaudel.bank.common.command.BaseCommand;
import com.brodygaudel.bank.common.enums.Sex;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Command to create a new customer.
 *
 * <p>
 * This command is used to initiate the creation of a new customer. It includes all the necessary information
 * such as the National Identity Card (NIC), first name, last name, place of birth, date of birth, nationality, sex,
 * and creation timestamp. The unique identifier for the customer is inherited from the BaseCommand class.
 * </p>
 *
 * @see BaseCommand
 */
@Getter
public class CreateCustomerCommand extends BaseCommand<String> {

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
     * The timestamp indicating when the customer was created.
     */
    private final LocalDateTime creation;

    /**
     * Constructs a new instance of CreateCustomerCommand with the specified parameters.
     *
     * @param id       The unique identifier for the customer.
     * @param nic      The National Identity Card (NIC) of the customer.
     * @param firstname The first name of the customer.
     * @param name     The last name of the customer.
     * @param placeOfBirth The place of birth of the customer.
     * @param dateOfBirth The date of birth of the customer.
     * @param nationality The nationality of the customer.
     * @param sex      The sex of the customer.
     * @param creation The timestamp indicating when the customer was created.
     */
    public CreateCustomerCommand(String id, String nic, String firstname, String name, String placeOfBirth,
                                 LocalDate dateOfBirth, String nationality, Sex sex, LocalDateTime creation) {
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
