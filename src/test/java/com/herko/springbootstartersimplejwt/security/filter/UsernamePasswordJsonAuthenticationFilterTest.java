package com.herko.springbootstartersimplejwt.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herko.springbootstartersimplejwt.security.model.LoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

class UsernamePasswordJsonAuthenticationFilterTest {
    private UsernamePasswordJsonAuthenticationFilter filter;
    private AuthenticationManager authenticationManager;
    private HttpServletResponse httpServletResponse;

    @BeforeEach
    void beforeEach() {
        this.authenticationManager = Mockito.mock(AuthenticationManager.class);
        this.filter = new UsernamePasswordJsonAuthenticationFilter(authenticationManager, new ObjectMapper());
        this.httpServletResponse = Mockito.mock(HttpServletResponse.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void given_emptyRequestBody_When_attemptsAuthentication_Then_throwsInternalAuthenticationServiceException(final String requestBody) {
        // Given
        final HttpServletRequest mockHttpRequest = buildTestJsonRequest(requestBody).buildRequest(new MockServletContext());

        // When, Then
        Assertions.assertThrows(InternalAuthenticationServiceException.class, () -> filter.attemptAuthentication(mockHttpRequest, httpServletResponse));
    }

    @Test
    void given_loginRequest_When_attemptsAuthentication_Then_authenticationManagerCalledWithUsernamePasswordToken() throws JsonProcessingException {
        // Given
        final HttpServletRequest mockHttpRequest = buildTestJsonRequest(
                new ObjectMapper().writeValueAsString(new LoginRequest("test@test.com", "Test1234")))
                .buildRequest(new MockServletContext());

        // When
        filter.attemptAuthentication(mockHttpRequest, httpServletResponse);

        // Then
        Mockito.verify(authenticationManager).authenticate(
                Mockito.argThat(argument ->
                        Objects.equals(argument.getPrincipal(), "test@test.com")
                                && Objects.equals(argument.getCredentials(), "Test1234")
                                && !argument.isAuthenticated()));
    }

    private MockHttpServletRequestBuilder buildTestJsonRequest(final String requestBody) {
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/test")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        if (requestBody != null) {
            builder.content(requestBody);
        }

        return builder;
    }
}