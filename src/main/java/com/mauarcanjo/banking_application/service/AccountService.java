package com.mauarcanjo.banking_application.service;

import com.mauarcanjo.banking_application.dto.AccountDto;
import com.mauarcanjo.banking_application.dto.TransactionDto;
import com.mauarcanjo.banking_application.dto.TransferFundDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);
//    AccountDto updateAccount(AccountDto accountDto);
    AccountDto deposit(Long id, double amount);
    AccountDto withdraw(Long id, double amount);
    List<AccountDto> getAllAccounts(int pageNumber, int accountsPerPage);
    boolean deleteAccount(Long id);
    boolean transferFunds(TransferFundDto transferFundDto);
    List<TransactionDto> getAllTransactions (Long id);

}
