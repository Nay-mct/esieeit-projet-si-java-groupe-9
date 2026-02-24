package com.esieeit.projetsi.domain.model;

import java.util.UUID;

public class Task {

    public enum Status {
        TODO,
        DONE
    }

    private final String id;
    private final String projectId;
    private String title;
    private String description;
    private Status status;

    public Task(String projectId, String title, String description) {
        this.id = UUID.randomUUID().toString();
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.status = Status.TODO;
    }

    public String getId() {
        return id;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void update(String newTitle, String newDescription) {
        this.title = newTitle;
        this.description = newDescription;
    }

    public void markDone() {
        this.status = Status.DONE;
    }

    public void reopen() {
        this.status = Status.TODO;
    }
}