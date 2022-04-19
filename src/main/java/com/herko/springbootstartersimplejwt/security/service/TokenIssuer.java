package com.herko.springbootstartersimplejwt.security.service;

import com.herko.springbootstartersimplejwt.security.exception.TokenIssuanceException;

public interface TokenIssuer {
    String issueAccessToken(String subject) throws TokenIssuanceException;
    String issueRefreshToken(String subject) throws TokenIssuanceException;
}
