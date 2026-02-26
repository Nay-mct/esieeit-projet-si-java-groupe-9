package com.esieeit.projetsi.domain.model;

import com.esieeit.projetsi.domain.enums.TaskPriority;
import com.esieeit.projetsi.domain.enums.TaskStatus;
import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import com.esieeit.projetsi.domain.validation.Validators;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Domain entity representing a task inside a project.
 */
public class Task {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Project project;
    private User assignee;
    private LocalDate dueDate;
    private final Instant createdAt;
    private Instant updatedAt;

    public Task(String title, String description, Project project) {
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
        setTitle(title);
        setDescription(description);
        setProject(project);
        this.status = TaskStatus.TODO;
        this.priority = TaskPriority.MEDIUM;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = Validators.requireNonBlank(title, "task.title", 1, 120);
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
        this.description = normalized.isEmpty() ? null
                : Validators.requireSize(normalized, "task.description", 1, 1000);
        touch();
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        Validators.requireNonNull(priority, "task.priority");
        this.priority = priority;
        touch();
    }

    public Project getProject() {
        return project;
    }

    public final void setProject(Project project) {
        Validators.requireNonNull(project, "task.project");
        this.project = project;
        touch();
    }

    public User getAssignee() {
        return assignee;
    }

    public void assignTo(User user) {
        Validators.requireNonNull(user, "task.assignee");
        this.assignee = user;
        touch();
    }

    public void unassign() {
        this.assignee = null;
        touch();
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        if (dueDate != null && dueDate.isBefore(LocalDate.now())) {
            throw new BusinessRuleException("task.dueDate cannot be in the past");
        }
        this.dueDate = dueDate;
        touch();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Allowed from TODO only.
     */
    public void start() {
        requireStatus(TaskStatus.TODO, "start() only allowed from TODO");
        this.status = TaskStatus.IN_PROGRESS;
        touch();
    }

    /**
     * Allowed from IN_PROGRESS only.
     */
    public void complete() {
        requireStatus(TaskStatus.IN_PROGRESS, "complete() only allowed from IN_PROGRESS");
        this.status = TaskStatus.DONE;
        touch();
    }

    /**
     * Returns a task to TODO when currently IN_PROGRESS.
     */
    public void moveBackToTodo() {
        requireStatus(TaskStatus.IN_PROGRESS, "moveBackToTodo() only allowed from IN_PROGRESS");
        this.status = TaskStatus.TODO;
        touch();
    }

    /**
     * Allowed from TODO or DONE according to current model decisions.
     */
    public void archive() {
        if (status != TaskStatus.TODO && status != TaskStatus.DONE) {
            throw new BusinessRuleException("archive() only allowed from TODO or DONE");
        }
        this.status = TaskStatus.ARCHIVED;
        touch();
    }

    private void requireStatus(TaskStatus expected, String message) {
        if (this.status != expected) {
            throw new BusinessRuleException(message + " (current=" + status + ")");
        }
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

    @Override
    public String toString() {
        return "Task{id=" + id + ", title='" + title + "', status=" + status + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Task other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
