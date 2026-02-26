package com.esieeit.projetsi.domain.model;

import com.esieeit.projetsi.domain.enums.UserRole;
import com.esieeit.projetsi.domain.validation.Validators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * JPA entity representing an authenticated user.
 */
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
            @UniqueConstraint(name = "uk_users_username", columnNames = "username")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Size(max = 150)
    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Size(max = 255)
    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private UserRole role = UserRole.USER;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Project> ownedProjects = new HashSet<>();

    @OneToMany(mappedBy = "assignee", fetch = FetchType.LAZY)
    private Set<Task> assignedTasks = new HashSet<>();

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected User() {
        // Required by JPA.
    }

    public User(String email, String username, UserRole role) {
        this(email, username, null, role);
    }

    public User(String email, String username, String passwordHash, UserRole role) {
        setEmail(email);
        setUsername(username);
        setPasswordHash(passwordHash);
        setRole(role == null ? UserRole.USER : role);
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
        this.username = Validators.requireNonBlank(username, "user.username", 3, 50);
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        if (passwordHash != null) {
            Validators.requireSize(passwordHash, "user.passwordHash", 10, 255);
        }
        this.passwordHash = passwordHash;
        this.updatedAt = Instant.now();
    }

    public UserRole getRole() {
        return role;
    }

    public final void setRole(UserRole role) {
        Validators.requireNonNull(role, "user.role");
        this.role = role;
        this.updatedAt = Instant.now();
    }

    public Set<Project> getOwnedProjects() {
        return ownedProjects;
    }

    public Set<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }

    /**
     * Checks if the user owns a given role.
     */
    public boolean hasRole(UserRole role) {
        Validators.requireNonNull(role, "user.role");
        return this.role == role;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", email='" + email + "', username='" + username + "', role=" + role + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User other)) {
            return false;
        }
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
