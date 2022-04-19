package com.herko.springbootstartersimplejwt.security.service;

import com.herko.springbootstartersimplejwt.security.config.JwtConfig;
import com.herko.springbootstartersimplejwt.security.exception.TokenIssuanceException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.JWTClaimsSetVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.Mockito;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

class JwtTokenIssuerTest {
    private static final String TEST_SECRET = "_iVjXdyWVXV19m6cEvPkJ_PH_vouiyUgqSW5-1icIhLVoo1pORFBIaQLA0dIJJzSB2OX-ZTaTSxu_ge2EtkFGQToNIFw94oK72lBL_8oEZ8E6RNil837g1aU1M1S-3aPxeRNxziXy6oU-xw9Y0hy7ay-tINmAQ640gbht1v-bH0";
    private JwtConfig jwtConfig;

    @BeforeEach
    void beforeEach() {
        this.jwtConfig = Mockito.mock(JwtConfig.class);
        Mockito.doReturn(60L).when(jwtConfig).getAccessTokenExpirationInMinutes();
        Mockito.doReturn(120L).when(jwtConfig).getRefreshTokenExpirationInMinutes();
        Mockito.doReturn(TEST_SECRET).when(jwtConfig).getSecretKey();
    }

    @Test
    void given_subject_When_issueAccessTokenCalled_Then_ReturnJwtStringAsEncodedTokenWithSubjectAsSubClaim() throws TokenIssuanceException, JOSEException, ParseException, BadJWTException {
        // Given
        final TokenIssuer jwtTokenIssuer = new JwtTokenIssuer(jwtConfig);
        final String subject = "test@email.com";

        // When
        final String encodedToken = jwtTokenIssuer.issueAccessToken(subject);
        Assumptions.assumeTrue(encodedToken != null);
        final JWT jwt = JWTParser.parse(encodedToken);

        // Then
        assertTokenCorrectness(jwt, 60 * 60);
    }

    @Test
    void given_subject_When_issueRefreshTokenCalled_Then_ReturnJwtStringAsEncodedTokenWithSubjectAsSubClaim() throws TokenIssuanceException, JOSEException, ParseException, BadJWTException {
        // Given
        final TokenIssuer jwtTokenIssuer = new JwtTokenIssuer(jwtConfig);
        final String subject = "test@email.com";

        // When
        final String encodedToken = jwtTokenIssuer.issueRefreshToken(subject);
        Assumptions.assumeTrue(encodedToken != null);
        final JWT jwt = JWTParser.parse(encodedToken);

        // Then
        assertTokenCorrectness(jwt, 60 * 120);
    }

    private void assertTokenCorrectness(final JWT jwt, final long expirationTime) throws ParseException, JOSEException, BadJWTException {
        Assertions.assertTrue(jwt instanceof SignedJWT);
        Assertions.assertEquals(Date.from(Instant.now().plusSeconds(expirationTime)).getTime(), jwt.getJWTClaimsSet().getExpirationTime().getTime(), 1);
        Assertions.assertTrue(((SignedJWT) jwt).verify(new MACVerifier(jwtConfig.getSecretKey())));
        new DefaultJWTClaimsVerifier<>(
                new JWTClaimsSet.Builder().subject("test@email.com").build(),
                new HashSet<>(List.of("sub", "exp"))).verify(jwt.getJWTClaimsSet(), null);
    }
}