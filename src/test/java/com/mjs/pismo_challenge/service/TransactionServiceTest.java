package com.mjs.pismo_challenge.service;

import com.mjs.pismo_challenge.dto.AccountResponseDTO;
import com.mjs.pismo_challenge.dto.CreateAccountRequestDTO;
import com.mjs.pismo_challenge.dto.CreateTransactionRequestDTO;
import com.mjs.pismo_challenge.dto.TransactionResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Test
    void shouldCreateTransactionSuccessfully() {
        // Create account first
        CreateAccountRequestDTO accountRequest = new CreateAccountRequestDTO("55544433322");
        AccountResponseDTO account = accountService.createAccount(accountRequest);

        CreateTransactionRequestDTO request = new CreateTransactionRequestDTO(
                account.getAccountId(),
                1L, // CASH PURCHASE
                new BigDecimal("150.00"));

        TransactionResponseDTO response = transactionService.createTransaction(request);

        assertNotNull(response);
        assertNotNull(response.getTransactionId());
        assertEquals(account.getAccountId(), response.getAccountId());
        assertEquals(1L, response.getOperationTypeId());
        assertEquals(new BigDecimal("150.00"), response.getAmount());
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFound() {
        CreateTransactionRequestDTO request = new CreateTransactionRequestDTO(
                9999L,
                1L,
                new BigDecimal("100.00"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.createTransaction(request);
        });

        assertTrue(exception.getMessage().contains("Account not found"));
    }

    @Test
    void shouldThrowExceptionWhenOperationTypeNotFound() {
        // Create account first
        CreateAccountRequestDTO accountRequest = new CreateAccountRequestDTO("66677788899");
        AccountResponseDTO account = accountService.createAccount(accountRequest);

        CreateTransactionRequestDTO request = new CreateTransactionRequestDTO(
                account.getAccountId(),
                99L, // Invalid Operation Type
                new BigDecimal("100.00"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.createTransaction(request);
        });

        assertTrue(exception.getMessage().contains("Operation Type not found"));
    }
}
