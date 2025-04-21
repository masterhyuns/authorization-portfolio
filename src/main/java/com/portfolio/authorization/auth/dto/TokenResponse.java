package com.portfolio.authorization.auth.dto;

public record TokenResponse(
        String access_token,
        String refresh_token,
        String token_type,
        int expires_in
) {}
