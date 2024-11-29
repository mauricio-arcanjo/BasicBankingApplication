package com.mauarcanjo.banking_application.mapper;

import com.mauarcanjo.banking_application.dto.AccountDto;
import com.mauarcanjo.banking_application.model.entities.Account;

public class AccountMapper {

    public static Account mapToAccount (AccountDto accountDto){

//        return new Account(
//                //For class AccountDto
//                accountDto.getId(),
//                accountDto.getAccountHolderName(),
//                accountDto.getBalance()
//        );

                //For record AccountDto
          return new Account(accountDto.id(),
                  accountDto.accountHolderName(),
                  accountDto.balance());

    }

    public static AccountDto mapToAccountDto (Account account){

        return new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
    }
}
