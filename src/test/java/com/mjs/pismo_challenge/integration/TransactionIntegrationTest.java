package com.mjs.pismo_challenge.integration;

import com.mjs.pismo_challenge.dto.CreateAccountRequestDTO;
import com.mjs.pismo_challenge.dto.CreateTransactionRequestDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TransactionIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void shouldCreateTransactionSuccessfully() {
        // First create an account
        CreateAccountRequestDTO accountRequest = new CreateAccountRequestDTO("11122233344");
        int accountId = given()
                .contentType(ContentType.JSON)
                .body(accountRequest)
                .when()
                .post("/accounts")
                .then()
                .statusCode(201)
                .extract()
                .path("account_id");

        // Create transaction
        CreateTransactionRequestDTO transactionRequest = new CreateTransactionRequestDTO(
                (long) accountId,
                1L, // CASH PURCHASE
                new BigDecimal("100.00"));

        given()
                .contentType(ContentType.JSON)
                .body(transactionRequest)
                .when()
                .post("/transactions")
                .then()
                .statusCode(201)
                .body("transaction_id", notNullValue()) // Assuming API returns transaction_id
                .body("account_id", equalTo(accountId))
                .body("operation_type_id", equalTo(1))
                .body("amount", equalTo(100.00f)); // JSON numbers might be float/double
    }
}
