package com.esieeit.projetsi.application.port;

import com.esieeit.projetsi.domain.enums.TaskStatus;
import com.esieeit.projetsi.domain.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(Long id);

    List<Task> findAll();

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByProjectId(Long projectId);

    List<Task> findByProjectIdAndStatus(Long projectId, TaskStatus status);

    List<Task> findByTitleContainingIgnoreCase(String keyword);

    long countByProjectId(Long projectId);

    boolean existsByProjectIdAndTitleIgnoreCase(Long projectId, String title);

    void deleteById(Long id);

    boolean existsById(Long id);
}
