package com.esieeit.projetsi.api.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TaskUpdateRequest {

    @Size(min = 3, max = 120, message = "title doit contenir entre 3 et 120 caractères")
    private String title;

    @Size(max = 2000, message = "description ne doit pas dépasser 2000 caractères")
    private String description;

    @Pattern(regexp = "TODO|IN_PROGRESS|DONE|ARCHIVED", message = "status doit être parmi TODO, IN_PROGRESS, DONE, ARCHIVED")
    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
