package com.mjs.pismo_challenge.service;

import com.mjs.pismo_challenge.dto.AccountResponseDTO;
import com.mjs.pismo_challenge.dto.CreateAccountRequestDTO;

public interface AccountService {
    AccountResponseDTO createAccount(CreateAccountRequestDTO accountDTO);

    AccountResponseDTO getAccountById(Long accountId);
}
