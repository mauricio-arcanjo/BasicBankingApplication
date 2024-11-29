package com.mauarcanjo.banking_application.model.repositories;

import com.mauarcanjo.banking_application.model.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long>, JpaRepository<Account, Long> {



}
