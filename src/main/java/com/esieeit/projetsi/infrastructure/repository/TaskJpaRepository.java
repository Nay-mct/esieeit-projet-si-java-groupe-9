package com.esieeit.projetsi.infrastructure.repository;

import com.esieeit.projetsi.domain.enums.TaskStatus;
import com.esieeit.projetsi.domain.model.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskJpaRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByProjectId(Long projectId);
}
