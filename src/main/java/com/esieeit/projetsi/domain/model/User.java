package com.esieeit.projetsi.domain.model;

import com.esieeit.projetsi.domain.enums.UserRole;
import com.esieeit.projetsi.domain.exception.ValidationException;
import com.esieeit.projetsi.domain.validation.Validators;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Domain entity representing an authenticated user.
 */
public class User {

    private Long id;
    private String email;
    private String username;
    private String passwordHash;
    private Set<UserRole> roles;
    private final Instant createdAt;

    public User(String email, String username, Set<UserRole> roles) {
        this.createdAt = Instant.now();
        setEmail(email);
        setUsername(username);
        setRoles(roles);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = Validators.requireEmail(email, "user.email");
    }

    public String getUsername() {
        return username;
    }

    public final void setUsername(String username) {
        this.username = Validators.requireNonBlank(username, "user.username", 3, 30);
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        if (passwordHash != null) {
            Validators.requireSize(passwordHash, "user.passwordHash", 10, 255);
        }
        this.passwordHash = passwordHash;
    }

    public Set<UserRole> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public final void setRoles(Set<UserRole> roles) {
        Validators.requireNonNull(roles, "user.roles");
        if (roles.isEmpty()) {
            throw new ValidationException("user.roles", "must contain at least one role");
        }
        this.roles = new HashSet<>(roles);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Checks if the user owns a given role.
     */
    public boolean hasRole(UserRole role) {
        Validators.requireNonNull(role, "user.role");
        return roles.contains(role);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", email='" + email + "', username='" + username + "', roles=" + roles + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
