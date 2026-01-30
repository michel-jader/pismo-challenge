package com.mjs.pismo_challenge.service;

import com.mjs.pismo_challenge.dto.CreateTransactionRequestDTO;
import com.mjs.pismo_challenge.dto.TransactionResponseDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;


@Validated
public interface TransactionService {
    TransactionResponseDTO createTransaction(@Valid CreateTransactionRequestDTO transactionDTO);
}
