package com.brodygaudel.bank.query.repository;

import com.brodygaudel.bank.query.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("select a from Account a where a.customer.id =?1")
    Account findByCustomerId(String customerId);
}
