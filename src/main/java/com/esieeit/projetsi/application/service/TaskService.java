package com.esieeit.projetsi.application.service;

import com.esieeit.projetsi.api.dto.TaskCreateRequest;
import com.esieeit.projetsi.api.dto.TaskUpdateRequest;
import com.esieeit.projetsi.application.port.TaskRepository;
import com.esieeit.projetsi.domain.enums.TaskStatus;
import com.esieeit.projetsi.domain.enums.UserRole;
import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import com.esieeit.projetsi.domain.exception.InvalidDataException;
import com.esieeit.projetsi.domain.exception.ResourceNotFoundException;
import com.esieeit.projetsi.domain.model.Project;
import com.esieeit.projetsi.domain.model.Task;
import com.esieeit.projetsi.domain.model.User;
import com.esieeit.projetsi.domain.validation.Validators;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final Project defaultProject;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        User systemOwner = new User("system@esieeit.local", "system", "temporary-hash", UserRole.ADMIN);
        this.defaultProject = new Project("Default API Project", "Temporary project for TP 3.1", systemOwner);
    }

    public Task create(TaskCreateRequest request) {
        Validators.requireNonNull(request, "request");
        if (defaultProject.getId() != null
                && taskRepository.existsByProjectIdAndTitleIgnoreCase(defaultProject.getId(), request.getTitle())) {
            throw new BusinessRuleException("A task with the same title already exists in the project");
        }
        Task task = new Task(request.getTitle(), request.getDescription(), defaultProject);
        Task saved = taskRepository.save(task);
        if (defaultProject.getId() == null && saved.getProject() != null && saved.getProject().getId() != null) {
            defaultProject.setId(saved.getProject().getId());
        }
        return saved;
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public List<Task> getAll(String statusRaw, Long projectId, String keyword) {
        String normalizedKeyword = keyword == null ? null : keyword.trim();
        if (normalizedKeyword != null && !normalizedKeyword.isEmpty()) {
            return taskRepository.findByTitleContainingIgnoreCase(normalizedKeyword);
        }

        TaskStatus status = parseStatus(statusRaw);
        if (status != null && projectId != null) {
            return taskRepository.findByProjectIdAndStatus(projectId, status);
        }
        if (status != null) {
            return taskRepository.findByStatus(status);
        }
        if (projectId != null) {
            return taskRepository.findByProjectId(projectId);
        }
        return taskRepository.findAll();
    }

    public Task getById(Long id) {
        validateId(id);
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: id=" + id));
    }

    public Task update(Long id, TaskUpdateRequest request) {
        validateId(id);
        Validators.requireNonNull(request, "request");

        Task task = getById(id);

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        if (request.getStatus() != null) {
            applyStatusTransition(task, request.getStatus());
        }

        return taskRepository.save(task);
    }

    public void delete(Long id) {
        validateId(id);
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found: id=" + id);
        }
        taskRepository.deleteById(id);
    }

    private void applyStatusTransition(Task task, String statusRaw) {
        TaskStatus target = parseStatus(statusRaw);
        if (target == null) {
            throw new InvalidDataException("Unknown status: " + statusRaw);
        }

        TaskStatus current = task.getStatus();
        if (current == target) {
            return;
        }

        if (current == TaskStatus.ARCHIVED) {
            throw new BusinessRuleException("No transition allowed from ARCHIVED");
        }

        switch (target) {
            case TODO -> {
                if (current == TaskStatus.IN_PROGRESS) {
                    task.moveBackToTodo();
                    return;
                }
                throw new BusinessRuleException("Transition to TODO is allowed only from IN_PROGRESS");
            }
            case IN_PROGRESS -> task.start();
            case DONE -> task.complete();
            case ARCHIVED -> task.archive();
            default -> throw new BusinessRuleException("Unsupported status transition to " + target);
        }
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidDataException("id must be greater than 0");
        }
    }

    private TaskStatus parseStatus(String statusRaw) {
        if (statusRaw == null || statusRaw.isBlank()) {
            return null;
        }
        try {
            return TaskStatus.valueOf(statusRaw.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new InvalidDataException("Unknown status: " + statusRaw);
        }
    }
}
