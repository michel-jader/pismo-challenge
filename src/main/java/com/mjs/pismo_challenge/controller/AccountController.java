package com.mjs.pismo_challenge.controller;

import com.mjs.pismo_challenge.dto.AccountResponseDTO;
import com.mjs.pismo_challenge.dto.CreateAccountRequestDTO;
import com.mjs.pismo_challenge.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody CreateAccountRequestDTO accountDTO) {
        AccountResponseDTO createdAccount = accountService.createAccount(accountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccount(@PathVariable Long accountId) {
        AccountResponseDTO account = accountService.getAccountById(accountId);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }
}
