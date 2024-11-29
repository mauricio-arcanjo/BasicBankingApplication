package com.mauarcanjo.banking_application.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    private double amount;

    @Enumerated(EnumType.STRING) // Salva como texto no banco de dados
    private TransactionType transactionType; // Adicione o enum como um campo
    public enum TransactionType {DEPOSIT, WITHDRAW, TRANSFER};

    private Long transferReceiverId;

    private LocalDateTime timestamp;

}
