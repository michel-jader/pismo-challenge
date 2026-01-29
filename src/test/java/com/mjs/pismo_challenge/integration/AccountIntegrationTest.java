package com.mjs.pismo_challenge.integration;

import com.mjs.pismo_challenge.dto.CreateAccountRequestDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AccountIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void shouldCreateAccountSuccessfully() {
        CreateAccountRequestDTO request = new CreateAccountRequestDTO("12345678900");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/accounts")
                .then()
                .statusCode(201)
                .body("account_id", notNullValue())
                .body("document_number", equalTo("12345678900"));
    }

    @Test
    void shouldGetAccountByIdSuccessfully() {
        // First create an account
        CreateAccountRequestDTO request = new CreateAccountRequestDTO("98765432100");
        int accountId = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/accounts")
                .then()
                .statusCode(201)
                .extract()
                .path("account_id");

        // Then retrieve it
        given()
                .when()
                .get("/accounts/{id}", accountId)
                .then()
                .statusCode(200)
                .body("account_id", equalTo(accountId))
                .body("document_number", equalTo("98765432100"));
    }
}
