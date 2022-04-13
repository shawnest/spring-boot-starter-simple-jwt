package com.herko.springbootstartersimplejwt.integrationtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herko.springbootstartersimplejwt.security.config.JwtConfig;
import com.herko.springbootstartersimplejwt.security.config.WebSecurityConfig;
import com.herko.springbootstartersimplejwt.security.filter.UsernamePasswordJsonAuthenticationFilter;
import com.herko.springbootstartersimplejwt.security.model.LoginRequest;
import com.herko.springbootstartersimplejwt.security.model.LoginResponse;
import com.herko.springbootstartersimplejwt.security.service.JwtTokenIssuer;
import com.herko.springbootstartersimplejwt.utilities.TestController;
import com.herko.springbootstartersimplejwt.utilities.TestRequestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@WebMvcTest
@ContextConfiguration(classes = { WebSecurityConfig.class, JwtTokenIssuer.class, JwtConfig.class, TestController.class })
public class LoginTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    SecurityFilterChain securityFilterChain;

    @Test
    void given_guestUserWithIncorrectCredentials_When_triesToLogin_Then_receivesUnauthorizedResponse() throws Exception {
        // Given
        final LoginRequest loginRequest = new LoginRequest("test@test.com", "Invalid");

        // When
        final ResultActions result = mvc.perform(buildLoginJsonRequest(loginRequest));

        // Then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void given_guestUserWithCorrectCredentials_When_triesToLogin_Then_receivesTokens() throws Exception {
        // Given, When, Then
        loginWithValidCredentials().andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void given_guestUserWithCorrectCredentials_When_triesToLoginAndSuccess_Then_canAccessTestEndpoint() throws Exception {
        // Given
        final MvcResult result = loginWithValidCredentials().andReturn();
        final LoginResponse loginResponse = new ObjectMapper().readValue(result.getResponse().getContentAsByteArray(), LoginResponse.class);

        // When
        final RequestBuilder requestBuilder = TestRequestBuilder.buildGetJsonRequest(TestController.ENDPOINT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.accessToken());

        // When
        final ResultActions testEndpointResult = mvc.perform(requestBuilder);

        // Then
        testEndpointResult.andExpect(MockMvcResultMatchers.status().isOk());
    }

    private ResultActions loginWithValidCredentials() throws Exception {
        final LoginRequest loginRequest = new LoginRequest("test@test.com", "Test12345");
        whenAuthenticatedReturnSuccess();

        return mvc.perform(buildLoginJsonRequest(loginRequest));
    }

    private void whenAuthenticatedReturnSuccess() {
        final UsernamePasswordJsonAuthenticationFilter usernamePasswordJsonAuthenticationFilter = securityFilterChain.getFilters()
                .stream()
                .filter(filter -> filter.getClass().equals(UsernamePasswordJsonAuthenticationFilter.class))
                .map(UsernamePasswordJsonAuthenticationFilter.class::cast)
                .findFirst().orElseThrow();
        usernamePasswordJsonAuthenticationFilter.setAuthenticationManager(authentication -> new UsernamePasswordAuthenticationToken("test@test.com", "Test12345", Collections.emptyList()));
    }

    private MockHttpServletRequestBuilder buildLoginJsonRequest(final LoginRequest loginRequest) throws JsonProcessingException {
        return MockMvcRequestBuilders
                .post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest));
    }
}
