package com.herko.springbootstartersimplejwt.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herko.springbootstartersimplejwt.security.config.JwtConfig;
import com.herko.springbootstartersimplejwt.security.config.WebSecurityConfig;
import com.herko.springbootstartersimplejwt.security.exception.TokenIssuanceException;
import com.herko.springbootstartersimplejwt.security.model.LoginResponse;
import com.herko.springbootstartersimplejwt.security.service.JwtTokenIssuer;
import com.herko.springbootstartersimplejwt.security.service.TokenIssuer;
import com.herko.springbootstartersimplejwt.utilities.TestController;
import com.herko.springbootstartersimplejwt.utilities.TestRequestBuilder;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;


class LoginSuccessHandlerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TokenIssuer tokenIssuer = Mockito.mock(TokenIssuer.class);

    @Test
    void given_success_When_onAuthenticationSuccess_Then_willReturnLoginResponse() throws IOException, TokenIssuanceException {
        // Given
        Mockito.doReturn(TestRequestBuilder.VALID_JWT_TOKEN).when(tokenIssuer).issueAccessToken("test@test.com");
        Mockito.doReturn(TestRequestBuilder.VALID_JWT_TOKEN).when(tokenIssuer).issueRefreshToken("test@test.com");
        final LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler(objectMapper, tokenIssuer);
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