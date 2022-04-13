package com.herko.springbootstartersimplejwt.security.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtConfig {
    @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}")
    @Getter
    private String secretKey;

    @Value("${spring.security.oauth2.resourceserver.jwt.algorithm}")
    private String algorithm;

    @Value("${spring.security.oauth2.authorizationserver.jwt.access-token.expiration-in-minutes}")
    @Getter
    private long accessTokenExpirationInMinutes;

    @Value("${spring.security.oauth2.authorizationserver.jwt.refresh-token.expiration-in-minutes}")
    @Getter
    private long refreshTokenExpirationInMinutes;

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(new SecretKeySpec(secretKey.getBytes(), algorithm)).build();
    }
}
