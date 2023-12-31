package com.brodygaudel.bank.query.repository;

import com.brodygaudel.bank.query.entity.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OperationRepository extends JpaRepository<Operation, String> {

    @Query("select o from Operation o where o.account.id =?1")
    Page<Operation> findAllByAccountId(String accountId, Pageable pageable);
}
