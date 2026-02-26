package com.esieeit.projetsi.infrastructure.repository;

import com.esieeit.projetsi.domain.enums.ProjectStatus;
import com.esieeit.projetsi.domain.model.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectJpaRepository extends JpaRepository<Project, Long> {

    List<Project> findByStatus(ProjectStatus status);

    Optional<Project> findByNameAndOwnerId(String name, Long ownerId);
}
