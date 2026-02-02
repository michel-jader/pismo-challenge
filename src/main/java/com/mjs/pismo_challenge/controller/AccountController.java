package com.mjs.pismo_challenge.controller;

import com.mjs.pismo_challenge.dto.AccountResponseDTO;
import com.mjs.pismo_challenge.dto.CreateAccountRequestDTO;
import com.mjs.pismo_challenge.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody CreateAccountRequestDTO accountDTO) {
        log.info("Received request to create account for document: {}", accountDTO.getDocumentNumber());
        AccountResponseDTO createdAccount = accountService.createAccount(accountDTO);
        log.info("Account created successfully with ID: {}", createdAccount.getAccountId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccount(@PathVariable Long accountId) {
        log.info("Received request to get account with ID: {}", accountId);
        AccountResponseDTO account = accountService.getAccountById(accountId);
        if (account == null) {
            log.warn("Account with ID {} not found", accountId);
            return ResponseEntity.notFound().build();
        }
        log.info("Account retrieved successfully: {}", account);
        return ResponseEntity.ok(account);
    }
}
