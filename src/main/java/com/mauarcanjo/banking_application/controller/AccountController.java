package com.mauarcanjo.banking_application.controller;

import com.mauarcanjo.banking_application.dto.AccountDto;
import com.mauarcanjo.banking_application.dto.TransactionDto;
import com.mauarcanjo.banking_application.dto.TransferFundDto;
import com.mauarcanjo.banking_application.model.entities.Transaction;
import com.mauarcanjo.banking_application.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //Add Account Rest API
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {

        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    //Get Account Rest API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {

        AccountDto accountDto = accountService.getAccountById(id);

        if (accountDto != null) {
            return new ResponseEntity<>(accountDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //Get All Accounts REST API
    @GetMapping("/all/{pageNumber}/{accountsPerPage}")
    public ResponseEntity<List<AccountDto>> getAllAccounts(@PathVariable int pageNumber, @PathVariable int accountsPerPage) {

        return new ResponseEntity<>(accountService.getAllAccounts(pageNumber, accountsPerPage), HttpStatus.OK);

    }

    //Deposit Rest API
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id,
                                              @RequestBody Map<String, Double> request) {

        Double amount = request.get("amount");
        AccountDto accountDto = accountService.deposit(id, amount);

        return ResponseEntity.ok(accountDto);
    }

    //Withdraw Rest API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,
                                               @RequestBody Map<String, Double> request) {

        Double amount = request.get("amount");
        AccountDto accountDto = accountService.withdraw(id, amount);

        return ResponseEntity.ok(accountDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {

        accountService.deleteAccount(id);

        return new ResponseEntity<>("Account is deleted successfully!", HttpStatus.OK);
    }


    @PutMapping("/transfer")
    public ResponseEntity<String> transferBetweenAccounts(@RequestBody TransferFundDto transferFundDto) {

        accountService.transferFunds(transferFundDto);

        String message = "Transfer of $" + transferFundDto.amount() +
                " sent from account " + transferFundDto.senderId() +
                " to account " + transferFundDto.receiverId();

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping ("/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactions(@PathVariable Long id){

        return new ResponseEntity<>(accountService.getAllTransactions(id), HttpStatus.OK);

    }


}
