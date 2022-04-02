package com.herko.springbootstartersimplejwt.integrationtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herko.springbootstartersimplejwt.security.model.LoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

class LoginTest {

}

//@WebMvcTest(value = LoginController.class, includeFilters = @ComponentScan.Filter(classes = EnableWebSecurity.class))
//class LoginTest {
//    @Autowired
//    private MockMvc mvc;
//    @MockBean
//    private AuthenticationManager authenticationManager;
//
//    @Test
//    void test_guestUserWithCorrectCredentials_receivesTokens() throws Exception {
//        final LoginRequest loginRequest = new LoginRequest("testuser@email.com", "Test123456");
//        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("testuser@email.com", "Test123456", List.of());
//
//        Mockito.doReturn(usernamePasswordAuthenticationToken).when(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken("testuser@email.com", "Test123456"));
//        final ResultActions resultActions = mvc.perform(buildLoginRequest(loginRequest));
//
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").isNotEmpty());
//        Assertions.assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
//    }
//
//    @Test
//    void test_guestUserWithInvalidCredentials_receivesUnauthorizedResponse() throws Exception {
//        final LoginRequest loginRequest = new LoginRequest("testuser@email.com", "InvalidPassword");
//        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("testuser@email.com", "InvalidPassword");
//
//        Mockito.doReturn(usernamePasswordAuthenticationToken).when(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken("testuser@email.com", "InvalidPassword"));
//        final MvcResult result = mvc.perform(buildLoginRequest(loginRequest)).andReturn();
//
//        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
//    }
//
//    private RequestBuilder buildLoginRequest(final LoginRequest loginRequest) throws JsonProcessingException {
//        final ObjectMapper objectMapper = new ObjectMapper();
//
//        return MockMvcRequestBuilders
//                .post("/login")
//                .with(csrf())
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(loginRequest));
//    }
//}
