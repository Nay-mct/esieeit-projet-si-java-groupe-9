package com.esieeit.projetsi.domain.model;

import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import com.esieeit.projetsi.domain.validation.Validators;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Domain entity representing a project owned by a user.
 */
public class Project {

    private Long id;
    private String name;
    private String description;
    private User owner;
    private final List<Task> tasks = new ArrayList<>();
    private final Instant createdAt;
    private Instant updatedAt;

    public Project(String name, String description, User owner) {
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
        setName(name);
        setDescription(description);
        setOwner(owner);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = Validators.requireNonBlank(name, "project.name", 1, 80);
        touch();
    }

    public String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        if (description == null) {
            this.description = null;
            touch();
            return;
        }
        String normalized = description.trim();
        if (normalized.isEmpty()) {
            this.description = null;
            touch();
            return;
        }
        this.description = Validators.requireSize(normalized, "project.description", 1, 500);
        touch();
    }

    public User getOwner() {
        return owner;
    }

    public final void setOwner(User owner) {
        Validators.requireNonNull(owner, "project.owner");
        this.owner = owner;
        touch();
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Business operation to rename a project.
     */
    public void rename(String newName) {
        setName(newName);
    }

    /**
     * Adds a task that must already belong to this project.
     */
    public void addTask(Task task) {
        Validators.requireNonNull(task, "project.task");
        if (task.getProject() != this) {
            throw new BusinessRuleException("task.project must reference this project");
        }
        this.tasks.add(task);
        touch();
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

    @Override
    public String toString() {
        return "Project{id=" + id + ", name='" + name + "', owner=" + owner.getUsername() + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Project other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
