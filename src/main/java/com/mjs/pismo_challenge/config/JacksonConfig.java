package com.mjs.pismo_challenge.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.TimeZone;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                // 1. Ensure the module for OffsetDateTime/Instant is registered
                .modules(new JavaTimeModule())
                // 2. Prevent writing dates as [2026, 1, 29, ...] arrays
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                // 3. Force the 'Z' suffix instead of +00:00 for UTC
                .featuresToDisable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)
                // 4. Global fallback timezone for the JSON parser
                .timeZone(TimeZone.getTimeZone("UTC"));
    }
}
