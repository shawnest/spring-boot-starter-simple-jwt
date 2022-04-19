package com.herko.springbootstartersimplejwt.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herko.springbootstartersimplejwt.security.exception.TokenIssuanceException;
import com.herko.springbootstartersimplejwt.security.model.LoginResponse;
import com.herko.springbootstartersimplejwt.security.service.TokenIssuer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final TokenIssuer tokenIssuer;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        LoginResponse loginResponse = null;

        try {
            loginResponse = new LoginResponse(
                    tokenIssuer.issueAccessToken(authentication.getPrincipal().toString()),
                    tokenIssuer.issueRefreshToken(authentication.getPrincipal().toString()));
        } catch (TokenIssuanceException e) {
            log.error("Failed to generate token for login response. Authentication {}", authentication, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        this.objectMapper.writeValue(response.getWriter(), loginResponse);
    }
}
