package com.mjs.pismo_challenge.service.impl;

import com.mjs.pismo_challenge.dto.AccountResponseDTO;
import com.mjs.pismo_challenge.dto.CreateAccountRequestDTO;
import com.mjs.pismo_challenge.entity.Account;
import com.mjs.pismo_challenge.repository.AccountRepository;
import com.mjs.pismo_challenge.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public AccountResponseDTO createAccount(CreateAccountRequestDTO accountDTO) {
        Account account = new Account(accountDTO.getDocumentNumber());
        Account savedAccount = accountRepository.save(account);
        return new AccountResponseDTO(savedAccount.getAccountId(), savedAccount.getDocumentNumber());
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .map(account -> new AccountResponseDTO(account.getAccountId(), account.getDocumentNumber()))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Account not found with ID: " + accountId));
    }
}
