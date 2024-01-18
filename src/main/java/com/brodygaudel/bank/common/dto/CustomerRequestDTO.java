package com.brodygaudel.bank.common.dto;

import com.brodygaudel.bank.common.enums.Sex;

import java.time.LocalDate;

public record CustomerRequestDTO(String nic, String firstname, String name,
                                 String placeOfBirth, LocalDate dateOfBirth,
                                 String nationality, Sex sex) {

}
