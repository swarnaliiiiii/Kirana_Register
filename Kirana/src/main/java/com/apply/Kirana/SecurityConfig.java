package com.apply.Kirana;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // In-memory authentication for simplicity
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        // Read-write user (admin)
        manager.createUser(User.withUsername("admin")
                .password("{noop}admin123") // {noop} means no password encoding for simplicity
                .roles("READ_WRITE")
                .build());

        // Read-only user
        manager.createUser(User.withUsername("user")
                .password("{noop}user123")
                .roles("READ_ONLY")
                .build());

        return manager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection for API (enable it in production if needed)
                .csrf(csrf -> csrf.disable())

                // Configure authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/transactions/all", "/api/reports/**").hasAnyRole("READ_WRITE", "READ_ONLY")  // Read-only access
                        .requestMatchers("/api/transactions/record", "/api/transactions/update/**", "/api/transactions/delete/**")
                        .hasRole("READ_WRITE")  // Write access
                        .anyRequest().authenticated()
                )

                // Enable HTTP Basic Authentication
                .httpBasic(Customizer.withDefaults());  // Modern way to enable basic auth

        return http.build();
    }
}

