package com.esieeit.projetsi.domain.model;

import com.esieeit.projetsi.domain.validation.Validators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Objects;

/**
 * JPA entity representing a comment posted on a task.
 */
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 2000)
    @Column(name = "content", nullable = false, length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comments_task"))
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comments_author"))
    private User author;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Comment() {
        // Required by JPA.
    }

    public Comment(String content, Task task, User author) {
        setContent(content);
        Validators.requireNonNull(task, "comment.task");
        this.task = task;
        Validators.requireNonNull(author, "comment.author");
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public final void setContent(String content) {
        this.content = Validators.requireNonBlank(content, "comment.content", 1, 2000);
        touch();
    }

    public Task getTask() {
        return task;
    }

    public final void setTask(Task task) {
        this.task = task;
        touch();
    }

    public User getAuthor() {
        return author;
    }

    public final void setAuthor(User author) {
        Validators.requireNonNull(author, "comment.author");
        this.author = author;
        touch();
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

    private void touch() {
        if (this.createdAt != null) {
            this.updatedAt = Instant.now();
        }
    }

    @Override
    public String toString() {
        return "Comment{id=" + id + ", content='" + content + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Comment other)) {
            return false;
        }
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
