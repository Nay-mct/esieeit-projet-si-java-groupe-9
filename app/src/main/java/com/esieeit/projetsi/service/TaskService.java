package com.esieeit.projetsi.service;

import com.esieeit.projetsi.domain.model.Task;
import com.esieeit.projetsi.exception.BusinessException;
import com.esieeit.projetsi.repository.TaskRepository;

import java.util.List;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // US-09: ajouter une tâche à un projet
    public Task addTask(String projectId, String title, String description) {
        if (projectId == null || projectId.isBlank()) {
            throw new BusinessException("Project id invalide");
        }
        if (title == null || title.isBlank()) {
            throw new BusinessException("Titre invalide");
        }

        Task task = new Task(projectId, title, description);
        taskRepository.save(task);
        return task;
    }

    // (utile) lister les tâches d'un projet
    public List<Task> listTasks(String projectId) {
        if (projectId == null || projectId.isBlank()) {
            throw new BusinessException("Project id invalide");
        }
        return taskRepository.findByProjectId(projectId);
    }

    // US-10: changer statut (TODO <-> DONE)
    public Task markDone(String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Tâche introuvable"));

        task.markDone();
        return task;
    }

    public Task reopen(String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Tâche introuvable"));

        task.reopen();
        return task;
    }

    // US-11: modifier une tâche
    public Task updateTask(String taskId, String newTitle, String newDescription) {
        if (newTitle == null || newTitle.isBlank()) {
            throw new BusinessException("Titre invalide");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Tâche introuvable"));

        task.update(newTitle, newDescription);
        return task;
    }

    // US-12: supprimer une tâche
    public void deleteTask(String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Tâche introuvable"));

        taskRepository.delete(task);
    }
    // US-13: filtrer les tâches d'un projet par statut
public List<Task> filterTasksByStatus(String projectId, Task.Status status) {
    if (projectId == null || projectId.isBlank()) {
        throw new BusinessException("Project id invalide");
    }
    if (status == null) {
        throw new BusinessException("Status invalide");
    }
    return taskRepository.findByProjectIdAndStatus(projectId, status);
}
// US-14: rechercher une tâche par mot-clé (dans titre + description)
public List<Task> searchTasks(String projectId, String keyword) {
    if (projectId == null || projectId.isBlank()) {
        throw new BusinessException("Project id invalide");
    }
    if (keyword == null || keyword.isBlank()) {
        throw new BusinessException("Mot-clé invalide");
    }
    return taskRepository.searchByProjectIdAndKeyword(projectId, keyword);
}
}