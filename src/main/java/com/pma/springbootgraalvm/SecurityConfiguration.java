package com.pma.springbootgraalvm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/my-account").authenticated()
                .anyRequest().permitAll())
            .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint((request, response, exception) -> {
                response.setStatus(HttpStatus.FOUND.value());
                response.setHeader(HttpHeaders.LOCATION, request.getContextPath() + "/login");
            }))
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .failureUrl("/login?error"))
            .logout(Customizer.withDefaults());
        return http.build();
    }
}