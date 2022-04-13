package com.herko.springbootstartersimplejwt.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herko.springbootstartersimplejwt.security.handler.SecurityExceptionHandler;
import com.herko.springbootstartersimplejwt.security.service.TokenIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final ObjectMapper objectMapper;
    private final TokenIssuer tokenIssuer;

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        final UserDetails userDetails = User
                .withUsername("akos.herko@rabit.hu")
                .password(passwordEncoder().encode("Test12345"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    SecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .sessionManagement().disable()
                .apply(new UsernamePasswordAuthenticationConfigurer(objectMapper, tokenIssuer))
                .and()
                .oauth2ResourceServer().jwt()
                .and()
                .authenticationEntryPoint(new SecurityExceptionHandler());

        return httpSecurity.build();
    }
}
