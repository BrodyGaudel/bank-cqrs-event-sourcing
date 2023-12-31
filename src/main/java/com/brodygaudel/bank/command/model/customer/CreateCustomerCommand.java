package com.brodygaudel.bank.command.model.customer;

import com.brodygaudel.bank.command.model.BaseCommand;
import com.brodygaudel.bank.query.enums.Sex;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CreateCustomerCommand extends BaseCommand<String> {
    private final String nic;
    private final String firstname;
    private final String name;
    private final String placeOfBirth;
    private final LocalDate dateOfBirth;
    private final String nationality;
    private final Sex sex;
    private final LocalDateTime creation;
    public CreateCustomerCommand(String id, String nic, String firstname, String name, String placeOfBirth, LocalDate dateOfBirth, String nationality, Sex sex, LocalDateTime creation) {
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
