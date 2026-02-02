package com.mjs.pismo_challenge.service;

import com.mjs.pismo_challenge.dto.AccountResponseDTO;
import com.mjs.pismo_challenge.dto.CreateAccountRequestDTO;
import com.mjs.pismo_challenge.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    void shouldCreateAccountSuccessfully() {
        CreateAccountRequestDTO request = new CreateAccountRequestDTO("99887766554");
        AccountResponseDTO response = accountService.createAccount(request);

        assertNotNull(response);
        assertNotNull(response.getAccountId());
        assertEquals("99887766554", response.getDocumentNumber());
    }

    @Test
    void shouldGetAccountByIdSuccessfully() {
        CreateAccountRequestDTO request = new CreateAccountRequestDTO("11223344556");
        AccountResponseDTO created = accountService.createAccount(request);

        AccountResponseDTO found = accountService.getAccountById(created.getAccountId());

        assertNotNull(found);
        assertEquals(created.getAccountId(), found.getAccountId());
        assertEquals("11223344556", found.getDocumentNumber());
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFound() {
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            accountService.getAccountById(9999L);
        });
        assertTrue(exception.getMessage().contains("Account not found with ID : '9999'"));
    }
}
