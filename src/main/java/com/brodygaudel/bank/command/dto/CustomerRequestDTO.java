package com.brodygaudel.bank.command.dto;

import com.brodygaudel.bank.query.enums.Sex;

import java.time.LocalDate;

public record CustomerRequestDTO(String nic, String firstname, String name,
                                 String placeOfBirth, LocalDate dateOfBirth,
                                 String nationality, Sex sex) {

}
