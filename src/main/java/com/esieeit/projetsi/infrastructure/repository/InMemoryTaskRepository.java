package com.esieeit.projetsi.infrastructure.repository;

import com.esieeit.projetsi.application.port.TaskRepository;
import com.esieeit.projetsi.domain.enums.TaskStatus;
import com.esieeit.projetsi.domain.model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("inmemory")
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Long, Task> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            long id = sequence.incrementAndGet();
            task.setId(id);
        }
        store.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {
        return store.values().stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findByProjectId(Long projectId) {
        return store.values().stream()
                .filter(task -> task.getProject() != null
                        && task.getProject().getId() != null
                        && task.getProject().getId().equals(projectId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findByProjectIdAndStatus(Long projectId, TaskStatus status) {
        return store.values().stream()
                .filter(task -> task.getStatus() == status)
                .filter(task -> task.getProject() != null
                        && task.getProject().getId() != null
                        && task.getProject().getId().equals(projectId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findByTitleContainingIgnoreCase(String keyword) {
        String normalized = keyword == null ? "" : keyword.toLowerCase();
        return store.values().stream()
                .filter(task -> task.getTitle() != null && task.getTitle().toLowerCase().contains(normalized))
                .collect(Collectors.toList());
    }

    @Override
    public long countByProjectId(Long projectId) {
        return store.values().stream()
                .filter(task -> task.getProject() != null
                        && task.getProject().getId() != null
                        && task.getProject().getId().equals(projectId))
                .count();
    }

    @Override
    public boolean existsByProjectIdAndTitleIgnoreCase(Long projectId, String title) {
        return store.values().stream()
                .anyMatch(task -> task.getProject() != null
                        && task.getProject().getId() != null
                        && task.getProject().getId().equals(projectId)
                        && task.getTitle() != null
                        && task.getTitle().equalsIgnoreCase(title));
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return store.containsKey(id);
    }
}
