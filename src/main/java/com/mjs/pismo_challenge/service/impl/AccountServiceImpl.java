package com.mjs.pismo_challenge.service.impl;

import com.mjs.pismo_challenge.dto.AccountResponseDTO;
import com.mjs.pismo_challenge.dto.CreateAccountRequestDTO;
import com.mjs.pismo_challenge.entity.Account;
import com.mjs.pismo_challenge.repository.AccountRepository;
import com.mjs.pismo_challenge.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public AccountResponseDTO createAccount(CreateAccountRequestDTO accountDTO) {
        log.info("Creating account for document: {}", accountDTO.getDocumentNumber());
        Account account = new Account(accountDTO.getDocumentNumber());
        Account savedAccount = accountRepository.save(account);
        log.debug("Account saved with ID: {}", savedAccount.getAccountId());
        return new AccountResponseDTO(savedAccount.getAccountId(), savedAccount.getDocumentNumber());
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountById(Long accountId) {
        log.debug("Fetching account with ID: {}", accountId);
        return accountRepository.findById(accountId)
                .map(account -> {
                    log.debug("Account found: {}", account);
                    return new AccountResponseDTO(account.getAccountId(), account.getDocumentNumber());
                })
                .orElseThrow(() -> {
                    log.warn("Account not found with ID: {}", accountId);
                    return new IllegalArgumentException("Account not found with ID: " + accountId);
                });
    }
}
