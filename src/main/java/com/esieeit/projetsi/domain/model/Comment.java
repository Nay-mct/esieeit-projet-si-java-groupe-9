package com.esieeit.projetsi.domain.model;

import com.esieeit.projetsi.domain.validation.Validators;
import java.time.Instant;
import java.util.Objects;

/**
 * Domain entity representing a comment posted on a task.
 */
public class Comment {

    private Long id;
    private String content;
    private Task task;
    private User author;
    private final Instant createdAt;

    public Comment(String content, Task task, User author) {
        this.createdAt = Instant.now();
        setContent(content);
        setTask(task);
        setAuthor(author);
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
        this.content = Validators.requireNonBlank(content, "comment.content", 1, 500);
    }

    public Task getTask() {
        return task;
    }

    public final void setTask(Task task) {
        Validators.requireNonNull(task, "comment.task");
        this.task = task;
    }

    public User getAuthor() {
        return author;
    }

    public final void setAuthor(User author) {
        Validators.requireNonNull(author, "comment.author");
        this.author = author;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Comment{id=" + id + ", author=" + author.getUsername() + ", content='" + content + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Comment other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
