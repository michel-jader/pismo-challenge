package com.mjs.pismo_challenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({ "com.mjs.pismo_challenge.repository" })
@EnableTransactionManagement
public class DatabaseConfiguration {

}
