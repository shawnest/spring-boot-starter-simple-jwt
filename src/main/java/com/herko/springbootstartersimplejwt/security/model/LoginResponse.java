package com.herko.springbootstartersimplejwt.security.model;

public record LoginResponse(String accessToken, String refreshToken) {
}
