package com.herko.springbootstartersimplejwt.security.service;

import com.herko.springbootstartersimplejwt.security.config.JwtConfig;
import com.herko.springbootstartersimplejwt.security.exception.TokenIssuanceException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenIssuer implements TokenIssuer {
    private final JwtConfig jwtConfig;

    @Override
    public String issueAccessToken(final String subject) throws TokenIssuanceException {
        return issue(subject, LocalDateTime.now(ZoneOffset.UTC).plusMinutes(jwtConfig.getAccessTokenExpirationInMinutes()));
    }

    @Override
    public String issueRefreshToken(final String subject) throws TokenIssuanceException {
        return issue(subject, LocalDateTime.now(ZoneOffset.UTC).plusMinutes(jwtConfig.getRefreshTokenExpirationInMinutes()));
    }

    private String issue(final String subject, final LocalDateTime expirationTime) throws TokenIssuanceException {
        try {
            final JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .expirationTime(Date.from(expirationTime.toInstant(ZoneOffset.UTC)))
                    .subject(subject)
                    .build();

            final JWSObject jws = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), claims.toPayload());
            jws.sign(new MACSigner(jwtConfig.getSecretKey()));

            return jws.serialize();
        } catch (final JOSEException e) {
            log.error("Issuance of token failed! Subject: {}, Expiration: {}", subject, expirationTime);
            throw new TokenIssuanceException(e);
        }
    }
}
