package com.mjs.pismo_challenge.dto;

import java.io.Serializable;

public class ValidationErrorDetail implements Serializable {

    private String field;
    private String issue;

    public ValidationErrorDetail() {
    }

    public ValidationErrorDetail(String field, String issue) {
        this.field = field;
        this.issue = issue;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }
}
