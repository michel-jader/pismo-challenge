package com.mjs.pismo_challenge.service.impl;

import com.mjs.pismo_challenge.dto.CreateTransactionRequestDTO;
import com.mjs.pismo_challenge.dto.TransactionResponseDTO;
import com.mjs.pismo_challenge.entity.Account;
import com.mjs.pismo_challenge.entity.OperationType;
import com.mjs.pismo_challenge.entity.Transaction;
import com.mjs.pismo_challenge.exception.ResourceNotFoundException;
import com.mjs.pismo_challenge.repository.AccountRepository;
import com.mjs.pismo_challenge.repository.OperationTypeRepository;
import com.mjs.pismo_challenge.repository.TransactionRepository;
import com.mjs.pismo_challenge.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mjs.pismo_challenge.utils.CONSTANTS.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final OperationTypeRepository operationTypeRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository,
            OperationTypeRepository operationTypeRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.operationTypeRepository = operationTypeRepository;
    }

    private BigDecimal discharge(
            OperationType.OperationDirection operationDirection,
            Long accountId,
            BigDecimal currentAmount
            ) {

        if (OperationType.OperationDirection.DEBIT.equals(operationDirection)) {
            return currentAmount;
        }

        //FIXME: get fom DB where direction is DEBIT
        // OR evolve query to join with op. type and get only DEBIT ones
        // OperationType IDs to discharge
        List<Long> operationTypeIds = Arrays.asList(1L, 2L, 3L);

        List<Transaction> transactionsShouldDischarge = transactionRepository.findAllToDischarge(accountId, operationTypeIds);

        List<Transaction> transactionsToDischarge = new ArrayList<>();

        //FIXME: is it a good way to create new BigDecimal.... check and evolve if needed
        BigDecimal totalAvailable = BigDecimal.valueOf(currentAmount.floatValue());

        for (Transaction t : transactionsShouldDischarge) {

            BigDecimal newBalance = new BigDecimal(t.getBalance().floatValue());

            BigDecimal amountToDischarge = t.getBalance();

            if (totalAvailable.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            if (totalAvailable.compareTo(amountToDischarge) >= 0) {

                // tA = 100
                // aD = 50

                // tA = 50
                // aD = 50

                totalAvailable = totalAvailable.subtract(amountToDischarge.negate());
                t.setBalance(BigDecimal.ZERO);
                transactionsToDischarge.add(t);

            } else {

                // tA = 10
                // aD = 50

                t.setBalance(amountToDischarge.negate().subtract(totalAvailable));
                totalAvailable = BigDecimal.ZERO;
                transactionsToDischarge.add(t);

            }

        }

        if (!transactionsToDischarge.isEmpty()) {
            transactionRepository.saveAll(transactionsToDischarge);
        }

        return totalAvailable;


    }

    @Override
    @Transactional
    public TransactionResponseDTO createTransaction(CreateTransactionRequestDTO transactionDTO) {
        log.info("Processing transaction for account ID: {} and operation type ID: {}",
                transactionDTO.getAccountId(), transactionDTO.getOperationTypeId());

        Account account = accountRepository.findById(transactionDTO.getAccountId())
                .orElseThrow(() -> {
                    log.error("Account not found for transaction: {}", transactionDTO.getAccountId());
                    return new ResourceNotFoundException(ACCOUNT_RESOURCE,  ACCOUNT_RESOURCE_ID, transactionDTO.getAccountId());
                });

        OperationType operationType = operationTypeRepository.findById(transactionDTO.getOperationTypeId())
                .orElseThrow(() -> {
                    log.error("Operation Type not found: {}", transactionDTO.getOperationTypeId());
                    return new ResourceNotFoundException(OPERATION_TYPE_RESOURCE,  OPERATION_TYPE_RESOURCE_ID, transactionDTO.getOperationTypeId());
                });

        BigDecimal transactionAmount = null;

        if (OperationType.OperationDirection.DEBIT.equals(operationType.getDirection())) {
            transactionAmount = transactionDTO.getAmount().negate();
        } else {
            transactionAmount = transactionDTO.getAmount();
        }
        log.debug("Calculated transaction amount: {}", transactionAmount);

        //FIXME Impl balance mechanics
        BigDecimal balance = discharge(operationType.getDirection(), account.getAccountId(), transactionAmount);

        Transaction transaction = new Transaction(account, operationType, transactionAmount, balance,
                LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transaction processed successfully with ID: {}", savedTransaction.getTransactionId());

        return new TransactionResponseDTO(
                savedTransaction.getTransactionId(),
                savedTransaction.getAccount().getAccountId(),
                savedTransaction.getOperationType().getOperationTypeId(),
                savedTransaction.getAmount(),
                savedTransaction.getEventDate());
    }
}
