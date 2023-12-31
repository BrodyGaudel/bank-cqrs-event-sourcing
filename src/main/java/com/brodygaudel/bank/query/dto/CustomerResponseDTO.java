package com.brodygaudel.bank.query.dto;

import com.brodygaudel.bank.query.enums.Sex;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class CustomerResponseDTO {
    private String id;
    private  String nic;
    private  String firstname;
    private  String name;
    private  String placeOfBirth;
    private  LocalDate dateOfBirth;
    private  String nationality;
    private  Sex sex;
    private  LocalDateTime creation;
    private  LocalDateTime lastUpdate;
}
