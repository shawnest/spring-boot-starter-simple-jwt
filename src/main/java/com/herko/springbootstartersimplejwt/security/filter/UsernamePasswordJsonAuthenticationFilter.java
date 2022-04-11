package com.herko.springbootstartersimplejwt.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herko.springbootstartersimplejwt.security.model.LoginRequest;
import com.nimbusds.jose.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsernamePasswordJsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final static Logger LOGGER = LoggerFactory.getLogger(UsernamePasswordJsonAuthenticationFilter.class);
    private final ObjectMapper objectMapper;

    public UsernamePasswordJsonAuthenticationFilter(final AuthenticationManager authenticationManager, final ObjectMapper objectMapper) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
        try {
            final LoginRequest loginRequest = this.objectMapper.readValue(IOUtils.readInputStreamToString(request.getInputStream()), LoginRequest.class);
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());

            setDetails(request, token);

            return this.getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            LOGGER.error("Failed to convert request from JSON to LoginRequest.", e);
            throw new InternalAuthenticationServiceException("Failed to convert request from JSON to LoginRequest.", e);
        }
    }
}
