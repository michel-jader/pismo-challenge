package com.mjs.pismo_challenge.controller.exception;

import com.mjs.pismo_challenge.controller.exception.GlobalExceptionHandlerTest.TestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class TestController {

    @PostMapping("/test/valid")
    public void testValid(@Valid @RequestBody TestDto dto) {
    }

    @GetMapping("/test/validated")
    public void testValidated(@RequestParam @NotEmpty String param) {
    }

    @GetMapping("/test/illegal")
    public void testIllegal() {
        throw new IllegalArgumentException("Invalid argument provided");
    }
}
