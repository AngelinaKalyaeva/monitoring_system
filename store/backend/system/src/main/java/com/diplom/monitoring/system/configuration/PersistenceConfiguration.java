package com.diplom.monitoring.system.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {
    private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
    private static final String SCHEMA_NAME = "production";
    private static final String URL = "jdbc:postgresql://localhost:5432/marketdb";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "632563256";

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setDriverClassName(POSTGRESQL_DRIVER);
        driverManagerDataSource.setSchema(SCHEMA_NAME);
        driverManagerDataSource.setUrl(URL);
        driverManagerDataSource.setUsername(USER_NAME);
        driverManagerDataSource.setPassword(PASSWORD);
        return driverManagerDataSource;
    }

    @Bean
    public JdbcTemplate postgreSqlJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
