package com.herko.springbootstartersimplejwt.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herko.springbootstartersimplejwt.security.model.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        final LoginResponse loginResponse = new LoginResponse("accessToken", "refreshToken");

        this.objectMapper.writeValue(response.getWriter(), loginResponse);
    }
}
