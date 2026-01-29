package com.mjs.pismo_challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class CreateAccountRequestDTO implements Serializable {

    @JsonProperty("document_number")
    private String documentNumber;

    public CreateAccountRequestDTO() {
    }

    public CreateAccountRequestDTO(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
