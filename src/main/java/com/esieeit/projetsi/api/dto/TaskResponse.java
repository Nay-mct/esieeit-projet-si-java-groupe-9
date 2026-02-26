package com.esieeit.projetsi.api.dto;

public class TaskResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final String status;

    public TaskResponse(Long id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}