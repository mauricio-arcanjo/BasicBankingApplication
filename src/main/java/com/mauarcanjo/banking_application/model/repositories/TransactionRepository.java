package com.mauarcanjo.banking_application.model.repositories;

import com.mauarcanjo.banking_application.model.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TransactionRepository  extends PagingAndSortingRepository<Transaction, Long>, JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountIdOrderByTimestampDesc(Long accountId);

}
