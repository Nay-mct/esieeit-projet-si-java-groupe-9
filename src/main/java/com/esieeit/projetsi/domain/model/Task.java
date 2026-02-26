package com.esieeit.projetsi.domain.model;

import com.esieeit.projetsi.domain.enums.TaskPriority;
import com.esieeit.projetsi.domain.enums.TaskStatus;
import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import com.esieeit.projetsi.domain.validation.Validators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * JPA entity representing a task inside a project.
 */
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 30)
    private TaskPriority priority;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_project"))
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", foreignKey = @ForeignKey(name = "fk_tasks_assignee"))
    private User assignee;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Task() {
        // Required by JPA.
    }

    public Task(String title, String description, Project project) {
        setTitle(title);
        setDescription(description);
        this.status = TaskStatus.TODO;
        this.priority = TaskPriority.MEDIUM;
        Validators.requireNonNull(project, "task.project");
        setProject(project);
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
        this.title = Validators.requireNonBlank(title, "task.title", 1, 200);
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
                : Validators.requireSize(normalized, "task.description", 1, 2000);
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

    public Set<Comment> getComments() {
        return Collections.unmodifiableSet(comments);
    }

    public void addComment(Comment comment) {
        if (comment == null) {
            return;
        }
        comments.add(comment);
        comment.setTask(this);
    }

    public void removeComment(Comment comment) {
        if (comment == null) {
            return;
        }
        comments.remove(comment);
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
        if (this.createdAt != null) {
            this.updatedAt = Instant.now();
        }
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
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
