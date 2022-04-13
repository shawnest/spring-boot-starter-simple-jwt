package com.herko.springbootstartersimplejwt.integrationtest;

import com.herko.springbootstartersimplejwt.security.config.JwtConfig;
import com.herko.springbootstartersimplejwt.security.config.WebSecurityConfig;
import com.herko.springbootstartersimplejwt.security.service.JwtTokenIssuer;
import com.herko.springbootstartersimplejwt.utilities.TestController;
import com.herko.springbootstartersimplejwt.utilities.TestRequestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
@ContextConfiguration(classes = { WebSecurityConfig.class, TestController.class, JwtTokenIssuer.class, JwtConfig.class })
@ActiveProfiles("test")
public class AuthenticationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void given_unauthenticatedUser_When_triesToAccessSecuredEndpoint_Then_ReceivesUnauthorizedResponse() throws Exception {
        // Given
        final RequestBuilder requestBuilder = TestRequestBuilder.buildGetJsonRequest(TestController.ENDPOINT);

        // When
        final ResultActions result = mvc.perform(requestBuilder);

        // Then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void given_userWithCorrectAccessToken_When_triesToAccessSecureEndpoint_Then_ReceivesTheEndpointResponse() throws Exception {
        // Given
        final RequestBuilder requestBuilder = TestRequestBuilder.buildGetJsonRequest(TestController.ENDPOINT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + TestRequestBuilder.VALID_JWT_TOKEN);

        // When
        final ResultActions result = mvc.perform(requestBuilder);

        // Then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
