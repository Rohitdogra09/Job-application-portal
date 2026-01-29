package com.rohit.jobportal.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AuthProviderConfig {

    private final DbUserDetailsService dbUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager() {
        // ✅ Spring Security 7: constructor requires UserDetailsService
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(dbUserDetailsService);

        // ✅ set BCrypt encoder so password matches work
        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }
}