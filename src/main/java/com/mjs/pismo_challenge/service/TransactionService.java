package com.mjs.pismo_challenge.service;

import com.mjs.pismo_challenge.dto.CreateTransactionRequestDTO;
import com.mjs.pismo_challenge.dto.TransactionResponseDTO;

public interface TransactionService {
    TransactionResponseDTO createTransaction(CreateTransactionRequestDTO transactionDTO);
}
