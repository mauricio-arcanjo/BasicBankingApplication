package com.mauarcanjo.banking_application.dto;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//@Data
//@AllArgsConstructor
//public class AccountDto {
//
//    private Long id;
//    private String accountHolderName;
//    private double balance;
//
//}

//Record was released in java 16 to provide a simpler way to transfer data between application layers
// and automatically provides getters, setters, constructors, tostring, etc.
public record AccountDto(Long id,
                         String accountHolderName,
                         double balance) {
}