package com.esieeit.projetsi.infrastructure.seed;

import com.esieeit.projetsi.domain.enums.ProjectStatus;
import com.esieeit.projetsi.domain.enums.TaskPriority;
import com.esieeit.projetsi.domain.enums.UserRole;
import com.esieeit.projetsi.domain.model.Project;
import com.esieeit.projetsi.domain.model.Task;
import com.esieeit.projetsi.domain.model.User;
import com.esieeit.projetsi.infrastructure.repository.ProjectJpaRepository;
import com.esieeit.projetsi.infrastructure.repository.TaskJpaRepository;
import com.esieeit.projetsi.infrastructure.repository.UserJpaRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DataInitializer {

    private final UserJpaRepository userRepository;
    private final ProjectJpaRepository projectRepository;
    private final TaskJpaRepository taskRepository;

    public DataInitializer(
            UserJpaRepository userRepository,
            ProjectJpaRepository projectRepository,
            TaskJpaRepository taskRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Bean
    CommandLineRunner initData() {
        return args -> {
            if (userRepository.count() > 0 || projectRepository.count() > 0 || taskRepository.count() > 0) {
                return;
            }

            User admin = new User("admin@esiee.local", "admin", "change_me_hash", UserRole.ADMIN);
            User alice = new User("alice@esiee.local", "alice", "change_me_hash", UserRole.USER);
            User bob = new User("bob@esiee.local", "bob", "change_me_hash", UserRole.USER);
            userRepository.saveAll(List.of(admin, alice, bob));

            Project p1 = new Project(
                    "Projet SI - Gestion des taches",
                    "Plateforme de gestion de projets et taches",
                    ProjectStatus.ACTIVE,
                    admin);
            Project p2 = new Project(
                    "Projet SI - Support interne",
                    "Gestion des tickets de support",
                    ProjectStatus.ACTIVE,
                    alice);
            projectRepository.saveAll(List.of(p1, p2));

            Task t1 = new Task("Initialiser le repository Git", "Creer le depot et les conventions", p1);
            t1.setDueDate(LocalDate.now().plusDays(1));
            t1.start();
            t1.complete();

            Task t2 = new Task("Creer les entites JPA", "Mapper User, Project, Task", p1);
            t2.setDueDate(LocalDate.now().plusDays(3));
            t2.setPriority(TaskPriority.HIGH);
            t2.start();
            t2.assignTo(bob);

            Task t3 = new Task("Creer TaskRepository", "Ajouter les query methods metier", p1);
            t3.setDueDate(LocalDate.now().plusDays(2));

            Task t4 = new Task("Preparer donnees de demonstration", "Jeu de donnees pour soutenance", p2);
            t4.setDueDate(LocalDate.now().plusDays(5));
            t4.setPriority(TaskPriority.URGENT);
            t4.assignTo(alice);

            taskRepository.saveAll(List.of(t1, t2, t3, t4));
        };
    }
}
