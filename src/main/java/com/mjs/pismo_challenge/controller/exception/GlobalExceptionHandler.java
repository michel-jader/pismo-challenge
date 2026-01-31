package com.mjs.pismo_challenge.controller.exception;

import com.mjs.pismo_challenge.dto.ErrorResponse;
import com.mjs.pismo_challenge.dto.ValidationErrorDetail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<ValidationErrorDetail> details = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String field = extractFieldFromPath(violation.getPropertyPath());
            details.add(new ValidationErrorDetail(field, violation.getMessage()));
        }

        ErrorResponse errorResponse = new ErrorResponse(
                "Failed due to invalid parameters.",
                details);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ValidationErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                "Failed due to invalid parameters.",
                details);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                null);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    private String extractFieldFromPath(Path path) {
        String field = null;
        for (Path.Node node : path) {
            field = node.getName();
        }
        return field;
    }
}