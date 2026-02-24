package com.esieeit.projetsi.domain.model;

import java.util.UUID;

public class Project {

    private final String id;
    private final String ownerUserId; // le propriétaire
    private String name;

    public Project(String ownerUserId, String name) {
        this.id = UUID.randomUUID().toString();
        this.ownerUserId = ownerUserId;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public String getName() {
        return name;
    }

    public void rename(String newName) {
        this.name = newName;
    }
}