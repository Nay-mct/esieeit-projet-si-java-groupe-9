package com.esieeit.projetsi.repository;

import com.esieeit.projetsi.domain.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskRepository {

    private final List<Task> tasks = new ArrayList<>();

    public void save(Task task) {
        tasks.add(task);
    }

    public Optional<Task> findById(String taskId) {
        return tasks.stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst();
    }

    public List<Task> findByProjectId(String projectId) {
        return tasks.stream()
                .filter(t -> t.getProjectId().equals(projectId))
                .collect(Collectors.toList());
    }

    public void delete(Task task) {
        tasks.remove(task);
    }
}