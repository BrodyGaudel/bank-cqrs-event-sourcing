package com.brodygaudel.bank.query.repository;

import com.brodygaudel.bank.query.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("SELECT c FROM Customer c WHERE c.name LIKE :keyword OR c.firstname LIKE :keyword OR c.nic LIKE :keyword ORDER BY c.firstname DESC")
    Page<Customer> search(@Param("keyword") String keyword, Pageable pageable);

    @Query("select case when count(c)>0 then true else false END from Customer c where c.nic=?1")
    Boolean checkIfNicExists(String nic);
}
