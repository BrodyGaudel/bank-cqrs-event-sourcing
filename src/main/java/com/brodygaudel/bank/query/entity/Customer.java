package com.brodygaudel.bank.query.entity;

import com.brodygaudel.bank.common.enums.Sex;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Customer {

    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String nic;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String placeOfBirth;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creation;

    private LocalDateTime lastUpdate;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Account account;
}
