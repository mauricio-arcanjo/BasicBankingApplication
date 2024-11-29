package com.mauarcanjo.banking_application.service.impl;

import com.mauarcanjo.banking_application.dto.AccountDto;
import com.mauarcanjo.banking_application.dto.TransactionDto;
import com.mauarcanjo.banking_application.dto.TransferFundDto;
import com.mauarcanjo.banking_application.exception.AccountException;
import com.mauarcanjo.banking_application.mapper.AccountMapper;
import com.mauarcanjo.banking_application.mapper.TransactionMapper;
import com.mauarcanjo.banking_application.model.entities.Account;
import com.mauarcanjo.banking_application.model.entities.Transaction;
import com.mauarcanjo.banking_application.model.repositories.AccountRepository;
import com.mauarcanjo.banking_application.model.repositories.TransactionRepository;
import com.mauarcanjo.banking_application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

@Service
public class AccountServiceImpl implements AccountService {

    // @Autowired - this annotation is not needed because since there is single a constructor spring automatically insert the dependency
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {

        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {


        Account receivedAccount = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account not found with ID: " + id));

        return AccountMapper.mapToAccountDto(receivedAccount);

//        return receivedAccount
//                .map(AccountMapper::mapToAccountDto) // Transforma o Account em AccountDto == map(receivedAccount -> AccountMapper.mapToAccountDto(receivedAccount))
//                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + account.getId())); // Lança exceção se não encontrado
    }

//    public AccountDto updateAccount(AccountDto accountDto) {
//
//        Account account = AccountMapper.mapToAccount(accountDto);
//        if (accountRepository.exists(account)){
//
//        }
//        Account savedAccount = accountRepository.exists(account);
//
//        return AccountMapper.mapToAccountDto(savedAccount);
//
//    }

    @Override
    public AccountDto deposit(Long id, double amount) {

        Account account = AccountMapper.mapToAccount(getAccountById(id));

        if (amount > 0) {
            account.setBalance(
                    account.getBalance() + amount
            );
            Account savedAccount = accountRepository.save(account);

            //Logging deposit
            Transaction transaction = new Transaction(
                    null,
                    account.getId(),
                    amount,
                    Transaction.TransactionType.DEPOSIT,
                    null,
                    LocalDateTime.now()
            );
            transactionRepository.save(transaction);

            return AccountMapper.mapToAccountDto(savedAccount);
        }

        return null;
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {

        Account account = AccountMapper.mapToAccount(getAccountById(id));

        if (amount <= account.getBalance()) {
            account.setBalance(
                    account.getBalance() - amount
            );
            Account savedAccount = accountRepository.save(account);

            //Logging withdraw
            Transaction transaction = new Transaction(
                    null,
                    account.getId(),
                    amount,
                    Transaction.TransactionType.WITHDRAW,
                    null,
                    LocalDateTime.now()
            );
            transactionRepository.save(transaction);

            return AccountMapper.mapToAccountDto(savedAccount);
        } else {
            throw new AccountException("Insufficient balance!");
        }

    }

    @Override
    public List<AccountDto> getAllAccounts(int pageNumber, int accountsPerPage) {

        PageRequest pageable = PageRequest.of(pageNumber, accountsPerPage);
        Iterable<Account> accounts = accountRepository.findAll(pageable);

        List<AccountDto> accountDtoList = StreamSupport.stream(accounts.spliterator(), false)
                .map(AccountMapper::mapToAccountDto)
                .toList();

        return accountDtoList;
    }

    @Override
    public boolean deleteAccount(Long id) {

        Account account = AccountMapper.mapToAccount(getAccountById(id));
        accountRepository.delete(account);
        return true;
    }

    @Override
    public boolean transferFunds(TransferFundDto transferFundDto) {
        //Retrieve sender account
        Account senderAccount = AccountMapper.mapToAccount(getAccountById(transferFundDto.senderId()));

        //Retrieve receiver id
        Account receiverAccount = AccountMapper.mapToAccount(getAccountById(transferFundDto.receiverId()));

        //Check if accounts are different
        if (!Objects.equals(senderAccount.getId(), receiverAccount.getId())) {
            //Check if sender balance is enough
            if (transferFundDto.amount() <= senderAccount.getBalance()) {

                //Debit from sender account
                senderAccount.setBalance(
                        senderAccount.getBalance() - transferFundDto.amount()
                );

                //Credit into receiver account
                receiverAccount.setBalance(
                        receiverAccount.getBalance() + transferFundDto.amount()
                );

                //Save operation
                accountRepository.save(senderAccount);
                accountRepository.save(receiverAccount);

                //Logging transfer
                Transaction transaction = new Transaction(
                        null,
                        senderAccount.getId(),
                        transferFundDto.amount(),
                        Transaction.TransactionType.TRANSFER,
                        receiverAccount.getId(),
                        LocalDateTime.now()
                );
                transactionRepository.save(transaction);

            } else {
                throw new AccountException("Insufficient balance!");
            }
        } else {
            throw new AccountException("Sender and receiver must be different accounts");
        }

        return true;
    }

    @Override
    public List<TransactionDto> getAllTransactions(Long id) {

        List<Transaction> transaction = transactionRepository.findByAccountIdOrderByTimestampDesc(id);

        return transaction.stream().map(TransactionMapper::mapToTransactionDto).toList();

    }

}
