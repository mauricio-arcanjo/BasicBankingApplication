package com.mauarcanjo.banking_application.dto;

public record TransferFundDto(
        Long senderId,
        Long receiverId,
        double amount
) { }
