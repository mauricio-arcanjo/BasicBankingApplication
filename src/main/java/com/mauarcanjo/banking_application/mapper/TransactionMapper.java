package com.mauarcanjo.banking_application.mapper;

import com.mauarcanjo.banking_application.dto.AccountDto;
import com.mauarcanjo.banking_application.dto.TransactionDto;
import com.mauarcanjo.banking_application.model.entities.Account;
import com.mauarcanjo.banking_application.model.entities.Transaction;

public class TransactionMapper {

    public static TransactionDto mapToTransactionDto(Transaction transaction) {

        return new TransactionDto(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getTransferReceiverId(),
                transaction.getTimestamp()
        );

    }
}
