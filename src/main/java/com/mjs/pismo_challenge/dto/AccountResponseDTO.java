package com.mjs.pismo_challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class AccountResponseDTO implements Serializable {

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("document_number")
    private String documentNumber;

    public AccountResponseDTO() {
    }

    public AccountResponseDTO(Long accountId, String documentNumber) {
        this.accountId = accountId;
        this.documentNumber = documentNumber;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
