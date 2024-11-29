package com.mauarcanjo.banking_application.dto;

import com.mauarcanjo.banking_application.model.entities.Account;
import com.mauarcanjo.banking_application.model.entities.Transaction;

import java.time.LocalDateTime;

public record TransactionDto(Long id,
                             Long accountId,
                             double amount,
                             Transaction.TransactionType transactionType,
                             Long receiverId,
                             LocalDateTime timestamp
) {
}
