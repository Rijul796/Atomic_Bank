package com.example.atomicbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF (Cross-Site Request Forgery)
                // This is required for Postman to work with POST requests
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Authorize Requests
                // "authorizeHttpRequests" tells Spring which endpoints are public/private
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated() // "Lock everything. User must be logged in."
                )

                // 3. Enable Basic Auth (for Postman)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}