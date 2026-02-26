package com.esieeit.projetsi.infrastructure.repository;

import com.esieeit.projetsi.application.port.TaskRepository;
import com.esieeit.projetsi.domain.enums.TaskStatus;
import com.esieeit.projetsi.domain.model.Project;
import com.esieeit.projetsi.domain.model.Task;
import com.esieeit.projetsi.domain.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Primary
@Transactional
public class JpaTaskRepositoryAdapter implements TaskRepository {

    private final TaskJpaRepository taskJpaRepository;
    private final ProjectJpaRepository projectJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public JpaTaskRepositoryAdapter(
            TaskJpaRepository taskJpaRepository,
            ProjectJpaRepository projectJpaRepository,
            UserJpaRepository userJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
        this.projectJpaRepository = projectJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Task save(Task task) {
        Project project = task.getProject();
        if (project == null) {
            throw new IllegalArgumentException("task.project must not be null");
        }

        User owner = project.getOwner();
        if (owner == null) {
            throw new IllegalArgumentException("project.owner must not be null");
        }

        User persistentOwner = resolveOwner(owner);
        project.setOwner(persistentOwner);

        Project persistentProject = resolveProject(project);
        task.setProject(persistentProject);

        User assignee = task.getAssignee();
        if (assignee != null) {
            task.assignTo(resolveOwner(assignee));
        }

        return taskJpaRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Task> findById(Long id) {
        return taskJpaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        return taskJpaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByStatus(TaskStatus status) {
        return taskJpaRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByProjectId(Long projectId) {
        return taskJpaRepository.findByProjectId(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByProjectIdAndStatus(Long projectId, TaskStatus status) {
        return taskJpaRepository.findByProjectIdAndStatus(projectId, status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByTitleContainingIgnoreCase(String keyword) {
        return taskJpaRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByProjectId(Long projectId) {
        return taskJpaRepository.countByProjectId(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByProjectIdAndTitleIgnoreCase(Long projectId, String title) {
        return taskJpaRepository.existsByProjectIdAndTitleIgnoreCase(projectId, title);
    }

    @Override
    public void deleteById(Long id) {
        taskJpaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return taskJpaRepository.existsById(id);
    }

    private User resolveOwner(User owner) {
        if (owner.getId() != null) {
            return userJpaRepository.findById(owner.getId()).orElseGet(() -> userJpaRepository.save(owner));
        }
        return userJpaRepository.findByEmail(owner.getEmail()).orElseGet(() -> userJpaRepository.save(owner));
    }

    private Project resolveProject(Project project) {
        if (project.getId() != null) {
            return projectJpaRepository.findById(project.getId()).orElseGet(() -> projectJpaRepository.save(project));
        }

        Long ownerId = project.getOwner().getId();
        if (ownerId != null) {
            Optional<Project> existing = projectJpaRepository.findByNameAndOwnerId(project.getName(), ownerId);
            if (existing.isPresent()) {
                return existing.get();
            }
        }
        return projectJpaRepository.save(project);
    }
}
