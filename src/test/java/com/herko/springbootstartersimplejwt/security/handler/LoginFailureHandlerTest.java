package com.herko.springbootstartersimplejwt.security.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;

import javax.servlet.http.HttpServletRequest;

class LoginFailureHandlerTest {
    @Test
    void given_failure_When_onAuthenticationFailure_Then_WillReturnUnauthorized() {
        // Given
        final LoginFailureHandler loginFailureHandler = new LoginFailureHandler();
        final MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

        // When
        loginFailureHandler.onAuthenticationFailure(Mockito.mock(HttpServletRequest.class), httpServletResponse, new BadCredentialsException("Bad credentials"));

        // Then
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), httpServletResponse.getStatus());
    }
}