package com.web.market.configuration;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource datasource = new DriverManagerDataSource();
        datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        datasource.setUrl("jdbc:mysql://localhost:3306/mydb");
        datasource.setUsername("mysql");
        datasource.setPassword("root");
        Properties properties = new Properties();
        properties.setProperty("useUnicode","yes");
        properties.setProperty("characterEncoding","utf-8");
        datasource.setConnectionProperties(properties);
        return datasource;
    }

}

