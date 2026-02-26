package com.esieeit.projetsi.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskCreateRequest {

    @NotBlank(message = "title est obligatoire")
    @Size(min = 3, max = 120, message = "title doit contenir entre 3 et 120 caractères")
    private String title;

    @Size(max = 2000, message = "description ne doit pas dépasser 2000 caractères")
    private String description;

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
}
