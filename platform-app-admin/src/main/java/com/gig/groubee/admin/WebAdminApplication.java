package com.gig.groubee.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gig.groubee.core.CoreConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@SpringBootApplication
@Import(CoreConfiguration.class)
public class WebAdminApplication extends SpringBootServletInitializer {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebAdminApplication.class);
    }

    @PostConstruct
    public void setUp() {
        objectMapper().registerModule(new JavaTimeModule());
    }

    public static void main(String[] args) {
        SpringApplication.run(WebAdminApplication.class, args);
    }
}
