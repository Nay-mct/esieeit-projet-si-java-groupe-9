package com.esieeit.projetsi.service;

import com.esieeit.projetsi.domain.model.Project;
import com.esieeit.projetsi.exception.BusinessException;
import com.esieeit.projetsi.repository.ProjectRepository;

import java.util.List;

public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // US-05: créer un projet
    public Project createProject(String ownerUserId, String name) {
        if (ownerUserId == null || ownerUserId.isBlank()) {
            throw new BusinessException("Owner user id invalide");
        }
        if (name == null || name.isBlank()) {
            throw new BusinessException("Nom de projet invalide");
        }

        Project project = new Project(ownerUserId, name);
        projectRepository.save(project);
        return project;
    }

    // US-06: lister projets
    public List<Project> listProjects(String ownerUserId) {
        if (ownerUserId == null || ownerUserId.isBlank()) {
            throw new BusinessException("Owner user id invalide");
        }
        return projectRepository.findByOwner(ownerUserId);
    }

    // US-07: renommer projet
    public Project renameProject(String ownerUserId, String projectId, String newName) {
        if (newName == null || newName.isBlank()) {
            throw new BusinessException("Nouveau nom invalide");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException("Projet introuvable"));

        if (!project.getOwnerUserId().equals(ownerUserId)) {
            throw new BusinessException("Accès refusé (projet non possédé)");
        }

        project.rename(newName);
        return project;
    }

    // US-08: supprimer projet
    public void deleteProject(String ownerUserId, String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException("Projet introuvable"));

        if (!project.getOwnerUserId().equals(ownerUserId)) {
            throw new BusinessException("Accès refusé (projet non possédé)");
        }

        projectRepository.delete(project);
    }
}