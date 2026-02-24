package com.esieeit.projetsi.repository;

import com.esieeit.projetsi.domain.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectRepository {

    private final List<Project> projects = new ArrayList<>();

    public void save(Project project) {
        projects.add(project);
    }

    public List<Project> findByOwner(String ownerUserId) {
        return projects.stream()
                .filter(p -> p.getOwnerUserId().equals(ownerUserId))
                .collect(Collectors.toList());
    }

    public Optional<Project> findById(String id) {
        return projects.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public void delete(Project project) {
        projects.remove(project);
    }
}