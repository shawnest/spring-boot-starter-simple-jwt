package com.herko.springbootstartersimplejwt.integrationtest;

import com.herko.springbootstartersimplejwt.security.config.WebSecurityConfig;
import com.herko.springbootstartersimplejwt.utilities.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest
@ContextConfiguration(classes = { WebSecurityConfig.class, TestController.class })
@ActiveProfiles("test")
public class AuthenticationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void given_unauthenticatedUser_When_triesToAccessSecuredEndpoint_Then_ReceivesUnauthorizedResponse() throws Exception {
        // Given
        final RequestBuilder requestBuilder = buildTestJsonRequest();

        // When
        final ResultActions result = mvc.perform(requestBuilder);

        // Then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void given_userWithCorrectAccessToken_When_triesToAccessSecureEndpoint_Then_ReceivesTheEndpointResponse() throws Exception {
        // Given
        final RequestBuilder requestBuilder = buildTestJsonRequest()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateValidToken());

        // When
        final ResultActions result = mvc.perform(requestBuilder);

        // Then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    private MockHttpServletRequestBuilder buildTestJsonRequest() {
        return MockMvcRequestBuilders
                .get("/test")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
    }

    /**
     * {
     *   "alg": "HS256",
     *   "typ": "JWT"
     * }
     * {
     *   "sub": "1234567890",
     *   "name": "John Doe",
     *   "iat": 1516239022,
     *   "exp": 1743244827
     * }
     * HMACSHA256(
     *   base64UrlEncode(header) + "." +
     *   base64UrlEncode(payload),
     *   (jwt secret key) _iVjXdyWVXV19m6cEvPkJ_PH_vouiyUgqSW5-1icIhLVoo1pORFBIaQLA0dIJJzSB2OX-ZTaTSxu_ge2EtkFGQToNIFw94oK72lBL_8oEZ8E6RNil837g1aU1M1S-3aPxeRNxziXy6oU-xw9Y0hy7ay-tINmAQ640gbht1v-bH0
     */
    private String generateValidToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE3NDMyNDQ4Mjd9.T4YYk8ifLj3zz_jYABedIQukHUqYARtUKVF_gGNF8gc";
    }
}
