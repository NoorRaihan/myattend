package com.uitm.myattend.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class DataSourceConfig {

    //data source config for hikari
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public HikariDataSource getDataSource() {
        return DataSourceBuilder.
                create().
                type(HikariDataSource.class).
                build();
    }

    //jdbc template bean init
    @Bean
    public JdbcTemplate db(HikariDataSource hikariDataSource) {
        return new JdbcTemplate(hikariDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(HikariDataSource hikariDataSource) {
        return new DataSourceTransactionManager(hikariDataSource);
    }
}
