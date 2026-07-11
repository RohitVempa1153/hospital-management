package com.example.hospitalmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    {
        httpSecurity.formLogin(formLogin->
            formLogin.disable()
        );

        return httpSecurity.build();
    }
}
