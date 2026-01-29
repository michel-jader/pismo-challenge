package com.mjs.pismo_challenge.service.impl;

import com.mjs.pismo_challenge.dto.CreateTransactionRequestDTO;
import com.mjs.pismo_challenge.dto.TransactionResponseDTO;
import com.mjs.pismo_challenge.entity.Account;
import com.mjs.pismo_challenge.entity.OperationType;
import com.mjs.pismo_challenge.entity.Transaction;
import com.mjs.pismo_challenge.repository.AccountRepository;
import com.mjs.pismo_challenge.repository.OperationTypeRepository;
import com.mjs.pismo_challenge.repository.TransactionRepository;
import com.mjs.pismo_challenge.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final OperationTypeRepository operationTypeRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository,
            OperationTypeRepository operationTypeRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.operationTypeRepository = operationTypeRepository;
    }

    @Override
    @Transactional
    public TransactionResponseDTO createTransaction(CreateTransactionRequestDTO transactionDTO) {
        Account account = accountRepository.findById(transactionDTO.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Account not found with ID: " + transactionDTO.getAccountId()));

        OperationType operationType = operationTypeRepository.findById(transactionDTO.getOperationTypeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Operation Type not found with ID: " + transactionDTO.getOperationTypeId()));

        Transaction transaction = new Transaction(account, operationType, transactionDTO.getAmount(),
                LocalDateTime.now());
        Transaction savedTransaction = transactionRepository.save(transaction);

        return new TransactionResponseDTO(
                savedTransaction.getTransactionId(),
                savedTransaction.getAccount().getAccountId(),
                savedTransaction.getOperationType().getOperationTypeId(),
                savedTransaction.getAmount(),
                savedTransaction.getEventDate());
    }
}
