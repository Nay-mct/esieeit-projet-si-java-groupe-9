package com.esieeit.projetsi.api.dto.auth;

public class AuthResponse {

    private final String token;
    private final String tokenType;
    private final long expiresInMs;
    private final Long userId;
    private final String username;
    private final String email;
    private final String role;

    public AuthResponse(
            String token,
            String tokenType,
            long expiresInMs,
            Long userId,
            String username,
            String email,
            String role) {
        this.token = token;
        this.tokenType = tokenType;
        this.expiresInMs = expiresInMs;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresInMs() {
        return expiresInMs;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
