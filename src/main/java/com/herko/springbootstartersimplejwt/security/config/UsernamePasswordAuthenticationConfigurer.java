package com.herko.springbootstartersimplejwt.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herko.springbootstartersimplejwt.security.filter.UsernamePasswordJsonAuthenticationFilter;
import com.herko.springbootstartersimplejwt.security.handler.LoginFailureHandler;
import com.herko.springbootstartersimplejwt.security.handler.LoginSuccessHandler;
import com.herko.springbootstartersimplejwt.security.service.TokenIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@RequiredArgsConstructor
public class UsernamePasswordAuthenticationConfigurer extends AbstractHttpConfigurer<UsernamePasswordAuthenticationConfigurer, HttpSecurity> {
    private final ObjectMapper objectMapper;
    private final TokenIssuer tokenIssuer;

    @Override
    public void configure(HttpSecurity http) {
        final AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        final UsernamePasswordJsonAuthenticationFilter filter = new UsernamePasswordJsonAuthenticationFilter(authenticationManager, objectMapper);
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler(objectMapper, tokenIssuer));
        filter.setAuthenticationFailureHandler(new LoginFailureHandler());

        http.addFilter(filter);
    }
}
