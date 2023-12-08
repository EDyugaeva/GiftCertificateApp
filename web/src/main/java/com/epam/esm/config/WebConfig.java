package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Class for configuration of web project
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.epam.esm"})
public class WebConfig {

}
