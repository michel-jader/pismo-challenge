package com.mjs.pismo_challenge.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Accounts")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Account_ID")
    private Long accountId;

    @Column(name = "Document_Number")
    private String documentNumber;

    public Account() {
    }

    public Account(String documentNumber) {
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
