package com.santiwrld.backend.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // enables @PreAuthorize on individual endpoints
public class SecurityConfig {

    private final ClerkJwtFilter clerkJwtFilter;

    public SecurityConfig(ClerkJwtFilter clerkJwtFilter) {
        this.clerkJwtFilter = clerkJwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // Stateless — Clerk manages sessions on the Next.js side,
                // Spring never creates or reads an HttpSession
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints — no token needed
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        // Everything else requires a valid Clerk token
                        .anyRequest().authenticated()
                )
                // Register your Clerk filter before Spring's default auth filter
                .addFilterBefore(clerkJwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
