package com.rohit.jobportal.auth;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresInSeconds
) {}