package com.herko.springbootstartersimplejwt.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herko.springbootstartersimplejwt.security.model.LoginResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;


class LoginSuccessHandlerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void given_success_When_onAuthenticationSuccess_Then_willReturnLoginResponse() throws ServletException, IOException {
        // Given
        final LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler(objectMapper);
        final MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

        // When
        loginSuccessHandler.onAuthenticationSuccess(
                Mockito.mock(HttpServletRequest.class),
                httpServletResponse,
                new UsernamePasswordAuthenticationToken("test@test.com", "Test12345", Collections.emptyList()));
        final LoginResponse loginResponse = objectMapper.readValue(httpServletResponse.getContentAsByteArray(), LoginResponse.class);

        // Then
        Assertions.assertTrue(HttpStatus.valueOf(httpServletResponse.getStatus()).is2xxSuccessful());
        Assertions.assertNotNull(loginResponse.accessToken());
        Assertions.assertNotNull(loginResponse.refreshToken());
    }
}