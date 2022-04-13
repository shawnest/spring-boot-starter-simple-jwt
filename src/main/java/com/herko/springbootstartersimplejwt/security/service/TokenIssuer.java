package com.herko.springbootstartersimplejwt.security.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;

public interface TokenIssuer {
    String issueAccessToken(String subject) throws JOSEException;
    String issueRefreshToken(String subject) throws JOSEException;
}
