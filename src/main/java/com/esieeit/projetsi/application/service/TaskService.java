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
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final Project defaultProject;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        User systemOwner = new User("system@esieeit.local", "system", Set.of(UserRole.ADMIN));
        this.defaultProject = new Project("Default API Project", "Temporary project for TP 3.1", systemOwner);
    }

    public Task create(TaskCreateRequest request) {
        Validators.requireNonNull(request, "request");
        Task task = new Task(request.getTitle(), request.getDescription(), defaultProject);
        defaultProject.addTask(task);
        return taskRepository.save(task);
    }

    public List<Task> getAll() {
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
        TaskStatus target;
        try {
            target = TaskStatus.valueOf(statusRaw.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
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
}
