package com.mjs.pismo_challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {

    private String message;
    private List<ValidationErrorDetail> details;

    public ErrorResponse() {
    }

    public ErrorResponse(String message,  List<ValidationErrorDetail> details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ValidationErrorDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ValidationErrorDetail> details) {
        this.details = details;
    }
}
