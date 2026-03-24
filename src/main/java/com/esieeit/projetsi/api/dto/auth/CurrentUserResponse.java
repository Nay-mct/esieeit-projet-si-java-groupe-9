package com.esieeit.projetsi.api.dto.auth;

import com.esieeit.projetsi.domain.model.User;

public class CurrentUserResponse {

    private final Long userId;
    private final String username;
    private final String email;
    private final String role;

    public CurrentUserResponse(Long userId, String username, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public static CurrentUserResponse from(User user) {
        return new CurrentUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getSecurityRole());
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
