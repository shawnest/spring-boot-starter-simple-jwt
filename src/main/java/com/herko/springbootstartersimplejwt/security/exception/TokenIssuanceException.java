package com.herko.springbootstartersimplejwt.security.exception;

public class TokenIssuanceException extends Exception {
    public TokenIssuanceException(final Throwable cause) {
        super(cause);
    }
}
