package com.mjs.pismo_challenge.controller;

import com.mjs.pismo_challenge.dto.CreateTransactionRequestDTO;
import com.mjs.pismo_challenge.dto.TransactionResponseDTO;
import com.mjs.pismo_challenge.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @RequestBody @Valid CreateTransactionRequestDTO transactionDTO) {
        log.info("Received request to create transaction for account ID: {}", transactionDTO.getAccountId());
        TransactionResponseDTO createdTransaction = transactionService.createTransaction(transactionDTO);
        log.info("Transaction created successfully with ID: {}", createdTransaction.getTransactionId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }
}
