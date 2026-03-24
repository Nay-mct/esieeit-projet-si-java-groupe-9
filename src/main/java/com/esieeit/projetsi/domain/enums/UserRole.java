package com.esieeit.projetsi.domain.enums;

/**
 * Security role granted to a user.
 */
public enum UserRole {
    USER,
    ADMIN;

    public String asSecurityRole() {
        return "ROLE_" + name();
    }
}
