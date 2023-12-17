package com.epam.esm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource(value = "classpath:application-${spring.profiles.active}.properties")
@Slf4j
@Profile({"dev", "prod"})
public class AppConfig {
    @Value("${database.driver.class.name}")
    private String driverClassName;
    @Value("${database.url}")
    private String url;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Bean
    public DataSource dataSource() {
        log.info("Active profile is {}", activeProfile );
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
