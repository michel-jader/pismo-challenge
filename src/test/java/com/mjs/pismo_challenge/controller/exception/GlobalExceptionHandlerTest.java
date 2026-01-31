package com.mjs.pismo_challenge.controller.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldHandleMethodArgumentNotValidException() throws Exception {
        TestDto testDto = new TestDto(); // Null field

        mockMvc.perform(post("/test/valid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Failed due to invalid parameters.")))
                .andExpect(jsonPath("$.details", hasSize(1)))
                .andExpect(jsonPath("$.details[0].field", is("field")))
                .andExpect(jsonPath("$.details[0].issue", is("must not be null")));
    }

    @Test
    void shouldHandleConstraintViolationException() throws Exception {
        mockMvc.perform(get("/test/validated")
                .param("param", "")) // Empty param should trigger validation
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Failed due to invalid parameters.")))
                .andExpect(jsonPath("$.details", hasSize(1)))
                .andExpect(jsonPath("$.details[0].field", is("param")))
                .andExpect(jsonPath("$.details[0].issue", notNullValue()));
    }

    @Test
    void shouldHandleIllegalArgumentException() throws Exception {
        mockMvc.perform(get("/test/illegal"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid argument provided")))
                .andExpect(jsonPath("$.details").doesNotExist());
    }

    static class TestDto {
        @NotNull
        public String field;
    }
}
